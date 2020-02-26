package com.envilabindonesia.android.ui.order.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.request.DocumentType
import com.envilabindonesia.android.data.request.SendDocumentRequest
import com.envilabindonesia.android.data.response.InvoiceData
import com.envilabindonesia.android.data.response.QuotationData
import com.envilabindonesia.android.data.response.TestResultResponse
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.*
import com.envilabindonesia.android.ui.order.add.OrderAddActivity
import com.envilabindonesia.android.ui.order.add.OrderAddServicesAdapter
import com.envilabindonesia.android.ui.review.ReviewListActivity
import com.envilabindonesia.android.util.PrefsUtil
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.item_progress_order.view.*
import kotlinx.android.synthetic.main.layout_share_invoice.view.*
import kotlinx.android.synthetic.main.layout_share_quotation.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class OrderDetailActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val VIEW_BUTTON_RESULT = 0
        const val VIEW_BUTTON_RESULT_RETRY = 1

        const val BUNDLE = "BUNDLE"
        fun start(context: Context, transactionResponse: TransactionResponse) {
            context.startActivity(Intent(context, OrderDetailActivity::class.java).apply {
                putExtra(BUNDLE, transactionResponse)
            })
        }
    }

    private var mAdapter: OrderAddServicesAdapter? = null
    private var transactionResponse: TransactionResponse? = null
    private var testResultResponse: TestResultResponse? = null
    private var dialogResult: AlertDialog? = null
    private var dialogChoiceQuotation: AlertDialog? = null
    private var dialogSendQuotation: SendDocumentDialog? = null
    private var dialogChoiceInvoice: AlertDialog? = null
    private var dialogSendInvoice: SendDocumentDialog? = null
    private var viewGeneratePdf: View? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelResult by lazy { getViewModel<OrderDetailViewModel>(viewModelFactory) }
    private val viewModelSendDoc by lazy { getViewModel<SendDocumentViewModel>(viewModelFactory) }
    private val viewModelQuotationInvoice by lazy { getViewModel<QuotationInvoiceViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_order_detail

    override fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.let { it.title = resources.getString(R.string.menu_order) }
        transactionResponse = intent.getParcelableExtra(BUNDLE)

        initHeader()
        initNotes()
        initButton()
        initPickedServices()

        observerResult()
        observerSendDoc()
        observerQuotationInvoice()

        if (transactionResponse?.status?.latest == Constant.TransactionStatus.COMPLETED) {
            requestTestResult()
        }
    }

    private fun observerQuotationInvoice() {
        with(viewModelQuotationInvoice) {
            onSuccess().observe(this@OrderDetailActivity, BaseObserver {
                it.result?.rows?.let { list ->
                    if (list.isNotEmpty()) {
                        transactionResponse = list[0]
                        when (viewGeneratePdf) {
                            include_quotation.btnForwardQuotation -> showChoiceQuotation()
                            include_invoice.btnForwardInvoice -> showChoiceInvoice()
                        }
                    }
                }
            })

            onError().observe(this@OrderDetailActivity, BaseObserver {
                it.toast(this@OrderDetailActivity)
            })

            onLoading().observe(this@OrderDetailActivity, BaseObserver {
                vf_quotaion_invoice.displayedChild = if (it) 1 else 0
            })
        }
    }

    private fun observerSendDoc() {
        viewModelSendDoc.onSuccess().observe(this, BaseObserver {
            it.message?.toast(applicationContext)
        })

        viewModelSendDoc.onError().observe(this, BaseObserver {
            it.toast(applicationContext)
        })

        viewModelSendDoc.onLoading().observe(this, BaseObserver {
            progressbar?.let { v -> v.visibility = if (it) View.VISIBLE else View.GONE }
        })
    }

    private fun requestTestResult() {
        transactionResponse?.orderId?.let {
            vf.displayedChild = VIEW_BUTTON_RESULT
            viewModelResult.getTestResult(it)
        }
    }

    private fun observerResult() {
        viewModelResult.onError().observe(this, BaseObserver {
            it.toast(this)
            vf.displayedChild = VIEW_BUTTON_RESULT_RETRY
        })

        viewModelResult.onLoading().observe(this, BaseObserver {
            isLoading(it)
        })

        viewModelResult.onSuccess().observe(this, BaseObserver {
            testResultResponse = it.result
        })
    }

    private fun initButton() {
        btnEdit.setOnClickListener(this)
        btnReview.setOnClickListener(this)
        btnRetry.setOnClickListener(this)

        when (transactionResponse?.status?.latest) {
            Constant.TransactionStatus.REQUEST -> {
                btnEdit.visibility = View.VISIBLE
                btnEdit.visibility = if (Constant.isLoginAsSales() && PrefsUtil.isOrderSubordinate()) View.GONE else View.VISIBLE
            }
            Constant.TransactionStatus.COMPLETED -> vf_button.visibility = View.VISIBLE
        }

        setLoadingButton(R.id.include_button_result, R.string.download_result) {
            val list = arrayListOf<String>()
            if (testResultResponse?.hardcopy == true) list.add(getString(R.string.test_result_hardcopy))
            if (testResultResponse?.softcopy == true) list.add(getString(R.string.test_result_softcopy))
            if (list.isEmpty()) {
                getString(R.string.hardcopy_softcopy_not_available).toast(this)
                return@setLoadingButton
            }

            dialogResult = getDialogChoice(list) {
                when (it) {
                    getString(R.string.test_result_hardcopy) -> {
                        val dialog = testResultResponse?.let { it1 -> HardcopyDialog.newInstance(it1) }
                        if (dialog?.isAdded == true) dialog.dismissAllowingStateLoss()
                        dialog?.show(supportFragmentManager, "")
                    }
                    getString(R.string.test_result_softcopy) -> dowloadPdf(testResultResponse?.pdfUrl ?: return@getDialogChoice)
                }
            }

            if (dialogResult?.isShowing == true) dialogResult?.dismiss()
            dialogResult?.show()
        }

        // quotation & invoice
        include_quotation.btnForwardQuotation.setOnClickListener(this)
        include_invoice.btnForwardInvoice.setOnClickListener(this)

        if (transactionResponse?.quotation?.isShow == true) {
            include_quotation.visibility = View.VISIBLE
            include_quotation.tvOrderCodeQuotation.text = transactionResponse?.quotationData?.number ?: "null"
        } else {
            include_quotation.visibility = View.GONE
            include_quotation.tvOrderCodeQuotation.text = ""
        }

        if (transactionResponse?.invoice?.isShow == true) {
            include_invoice.visibility =  View.VISIBLE
            include_invoice.tvOrderCodeInvoice.text = transactionResponse?.invoiceData?.number ?: "null"
        } else {
            include_invoice.visibility =  View.GONE
            include_invoice.tvOrderCodeInvoice.text = ""
        }

        btnReview.visibility = if (Constant.isLoginAsCompany()) View.VISIBLE else View.INVISIBLE
    }

    private fun dowloadPdf(url: String) {
        try {
            startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(Uri.parse(url), "application/pdf")
                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                }
            )
        } catch (e: Exception) {
            e.message?.toast(this)
        }
    }

    private fun initNotes() {
        transactionResponse?.notes?.let {
            if (it.isNotEmpty()) {
                layoutNotes.visibility = View.VISIBLE
                tvNotes.text = it.replaceNewlineHtml()
            }
        }
    }

    private fun initHeader() {
        transactionResponse?.let {

            with(chipStatus) {
                text = it.status?.label ?: "null"
                chipBackgroundColor = ColorStateList.valueOf(
                    Color.parseColor(
                        PrefsUtil.getTransactionStatusColor(it.status?.latest ?: 1)
                    )
                )
            }

            tvDate.text = it.created?.let { created -> TimeUtil.getHumanUTC(created) }
            tvName.text = it.companyName ?: "null"
            tvNumber.text = it.code
            tvCity.text = it.companyAddress
            tvPrice.text = it.totalPrice?.toIdr() ?: "null"

            it.progress?.forEach { progress ->
                val v = LayoutInflater.from(this).inflate(R.layout.item_progress_order, null)
                v.tvNameSchedule.text = progress.label
                v.tvDateSchedule.text = progress.date?.let { it2 ->
                    String.format(
                        it2.substringAfterLast("-") + " " +
                                TimeUtil.getHumanMonthYYYYMMDD(this, it2) + " " +
                                it2.substringBefore("-")
                    )
                }

                listProgress.addView(v)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            include_quotation.btnForwardQuotation, include_invoice.btnForwardInvoice -> {
                viewGeneratePdf = v
                viewModelQuotationInvoice.getTransactionByOrderId(transactionResponse?.orderId ?: return)
            }
            btnRetry -> requestTestResult()
            btnEdit -> editOrder()
            btnReview -> transactionResponse?.let { ReviewListActivity.start(this, it) }
        }
    }

    private fun showChoiceInvoice() {
        if (dialogChoiceInvoice == null) {
            val list = arrayListOf(getString(R.string.choice_preview), getString(R.string.choice_send))
            dialogChoiceInvoice = getDialogChoice(list) {
                when (it) {
                    getString(R.string.choice_preview) -> dowloadPdf(transactionResponse?.invoiceData?.pdf ?: return@getDialogChoice)
                    getString(R.string.choice_send) -> showSendInvoice()
                }
            }
        }

        if (dialogChoiceInvoice?.isShowing == true) dialogChoiceInvoice?.dismiss()
        dialogChoiceInvoice?.show()
    }

    private fun showSendInvoice() {
        if (dialogSendInvoice == null) {
            dialogSendInvoice = SendDocumentDialog.invoice(transactionResponse?.invoiceData ?: InvoiceData())
            dialogSendInvoice?.setOnListener(object : SendDocumentDialog.OnListener {
                override fun onSendInvoice(email: String, invoiceData: InvoiceData) {
                    transactionResponse?.orderId?.let {
                        viewModelSendDoc.postSendDocument(SendDocumentRequest(it, email, DocumentType.INVOICE.value))
                    } ?: getString(R.string.general_error).toast(applicationContext)
                }

                override fun onSendQuotation(email: String, quotationData: QuotationData) {
                    // do nothing
                }
            })
        }

        try {
            if (dialogSendInvoice?.isVisible == true) dialogSendInvoice?.dismissAllowingStateLoss()
            dialogSendInvoice?.show(supportFragmentManager, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showChoiceQuotation() {
        if (dialogChoiceQuotation == null) {
            val list = arrayListOf(getString(R.string.choice_preview), getString(R.string.choice_send))
            dialogChoiceQuotation = getDialogChoice(list) {
                when (it) {
                    getString(R.string.choice_preview) -> dowloadPdf(transactionResponse?.quotationData?.pdf ?: return@getDialogChoice)
                    getString(R.string.choice_send) -> showSendQuotation()
                }
            }
        }

        if (dialogChoiceQuotation?.isShowing == true) dialogChoiceQuotation?.dismiss()
        dialogChoiceQuotation?.show()
    }

    private fun showSendQuotation() {
        if (dialogSendQuotation == null) {
            dialogSendQuotation = SendDocumentDialog.quotation(transactionResponse?.quotationData ?: QuotationData())
            dialogSendQuotation?.setOnListener(object : SendDocumentDialog.OnListener {
                override fun onSendInvoice(email: String, invoiceData: InvoiceData) {
                    // do nothing
                }

                override fun onSendQuotation(email: String, quotationData: QuotationData) {
                    transactionResponse?.orderId?.let {
                        viewModelSendDoc.postSendDocument(SendDocumentRequest(it, email, DocumentType.QUOTATION.value))
                    } ?: getString(R.string.general_error).toast(applicationContext)
                }
            })
        }

        try {
            if (dialogSendQuotation?.isVisible == true) dialogSendQuotation?.dismissAllowingStateLoss()
            dialogSendQuotation?.show(supportFragmentManager, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun editOrder() {
        startActivityForResult(
            Intent(this, OrderAddActivity::class.java).apply {
                putExtra(OrderAddActivity.BUNDLE_EDIT, transactionResponse)
            }, Constant.ActivityResult.RC_EDIT_ORDER
        )
    }


    override fun onResume() {
        super.onResume()
        tvPrice.showCurrency()
    }

    private fun initPickedServices() {
        mAdapter = OrderAddServicesAdapter(false, false) { _, _, _, _ -> }

        rv.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailActivity)
            adapter = mAdapter
        }

        transactionResponse?.services?.forEach {
            mAdapter?.add(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constant.ActivityResult.RC_EDIT_ORDER -> finish()
            }
        }
    }

}