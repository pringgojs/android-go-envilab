package com.envilabindonesia.android.ui.order.add

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.request.OrderCancelRequest
import com.envilabindonesia.android.data.request.OrderCreateRequest
import com.envilabindonesia.android.data.request.OrderServices
import com.envilabindonesia.android.data.request.OrderUpdateRequest
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.ContactPersonResponse
import com.envilabindonesia.android.data.response.ServiceCorporateResponse
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.*
import com.envilabindonesia.android.ui.client.contact.list.ContactPersonListActivity
import com.envilabindonesia.android.ui.client.search.SearchClientActivity
import com.envilabindonesia.android.ui.order.OrderCancelDialog
import com.envilabindonesia.android.ui.order.OrderCancelViewModel
import com.envilabindonesia.android.ui.order.OrderUpdateViewModel
import com.envilabindonesia.android.ui.services.search.SearchServiceActivity
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.activity_order_add.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject


class OrderAddActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val VF_BUTTON_LOADING = 0
        const val VF_BUTTON_CREATE = 1
        const val VF_BUTTON_EDIT = 2
        const val VF_BUTTON_CREATE_DISABLED = 3

        const val BUNDLE_EDIT = "OrderAddActivity.BUNDLE"
    }

    private var list: ArrayList<ServiceCorporateResponse> = arrayListOf()
    private var mAdapter: OrderAddServicesAdapter? = null
    private var selectedCompany: CompanyResponse? = null
    private var selectedContact: ContactPersonResponse? = null

    private var transactionResponse: TransactionResponse? = null
    private var orderCancelDialog: OrderCancelDialog? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelMemberStatus by lazy { getViewModel<MemberStatusViewModel>(viewModelFactory) }
    private val viewModelCreate by lazy { getViewModel<OrderAddViewModel>(viewModelFactory) }
    private val viewModelDelete by lazy { getViewModel<OrderCancelViewModel>(viewModelFactory) }
    private val viewModelUpdate by lazy { getViewModel<OrderUpdateViewModel>(viewModelFactory) }

    override fun onClick(v: View?) {
        when (v) {
            tvCompany -> SearchClientActivity.startResult(this, Constant.ActivityResult.RC_SEARCH_CLIENT)
            tvServices -> SearchServiceActivity.startResult(this, Constant.ActivityResult.RC_SEARCH_SERVICE)
            tvContactPerson -> {
                ContactPersonListActivity.startResult(
                    this, selectedCompany ?: return,
                    Constant.ActivityResult.RC_SEARCH_CONTACT_PERSON
                )
            }
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_order_add

    override fun onResume() {
        super.onResume()
        tvPriceTotal.showCurrency()
    }

    override fun init() {
        initToolbar()
        initView()
        observeMember()
        observeCreate()
        observeDelete()
        observeUpdate()
        onClickListener()
        initPickedServices()
        initData()
        initEdit()
    }

    private fun showcaseChooseService() {
        showcaseViewRect(
            PrefsUtil.SHOWCASE_ORDER_SERVICE,
            R.id.tvServices,
            getString(R.string.showcase_order_service),
            getString(R.string.showcase_order_service_create)
        ) {}
    }

    private fun initEdit() {
        if (!intent.hasExtra(BUNDLE_EDIT)) {
            showcaseChooseService()
            vfButton.displayedChild = VF_BUTTON_CREATE
            viewModelMemberStatus.getMemberStatus()
            return
        }

        transactionResponse = intent.getParcelableExtra(BUNDLE_EDIT)

        setupCompany(
            CompanyResponse(
                companyId = transactionResponse?.companyId,
                companyName = transactionResponse?.companyName
            )
        )
        setupCompanyContact(
            ContactPersonResponse(
                cpId = transactionResponse?.cpId,
                cpName = transactionResponse?.cpName
            )
        )
        transactionResponse?.services?.forEach { insertServices(it) }
        transactionResponse?.notes?.let {
            if (it.isNotEmpty()) etNotes.setText(it.replaceNewlineHtml())
        }

        initEditButton()
    }

    private fun initEditButton() {
        vfButton.displayedChild = VF_BUTTON_EDIT
        tvCompany.setOnClickListener(null)
    }

    private fun observeMember() {
        viewModelMemberStatus.onLoading().observe(this, BaseObserver {
            isLoading(it)
        })

        viewModelMemberStatus.onError().observe(this, BaseObserver {
            it.toast(this)
            vfButton.displayedChild = VF_BUTTON_CREATE_DISABLED
        })

        viewModelMemberStatus.onSuccess().observe(this, BaseObserver {

            if (it.result == null) return@BaseObserver
            if (it.result.documentCompletion == null) return@BaseObserver
            if (it.result.enableOrder == null) return@BaseObserver

            val isCompleted = it.result.documentCompletion
            val isOrderEnabled = it.result.enableOrder
            val isCompanyNameFill = tvCompany.text.toString().trim().isNotEmpty()

            if (!isCompleted || !isOrderEnabled) {
                tvUncompleteDoc.visibility = View.VISIBLE
                vfButton.displayedChild = VF_BUTTON_CREATE_DISABLED
                if (!isOrderEnabled) tvUncompleteDoc.text = it.result.enableOrderMessage ?: getString(R.string.general_error)
            }

            if (Constant.isLoginAsCompany()) {
                if (!isCompanyNameFill) {
                    tvUncompleteDoc.visibility = View.VISIBLE
                    tvUncompleteDoc.text = getString(R.string.please_update_account_add_company)
                    vfButton.displayedChild = VF_BUTTON_CREATE_DISABLED
                }
            }
        })
    }

    private fun observeCreate() {
        viewModelCreate.onLoading().observe(this, BaseObserver {
            isLoading(it)
        })

        viewModelCreate.onError().observe(this, BaseObserver {
            it.toast(this)
        })

        viewModelCreate.onSuccess().observe(this, BaseObserver {
            it.message?.toast(this)
            finish()
        })
    }

    private fun observeDelete() {
        viewModelDelete.onLoading().observe(this, BaseObserver {
            vfButton.displayedChild = if (it) VF_BUTTON_LOADING else VF_BUTTON_EDIT
        })

        viewModelDelete.onError().observe(this, BaseObserver {
            it.toast(this)
        })

        viewModelDelete.onSuccess().observe(this, BaseObserver {
            it.message?.toast(this)
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    private fun observeUpdate() {
        viewModelUpdate.onLoading().observe(this, BaseObserver {
            isLoadingSecondary(it)
        })

        viewModelUpdate.onError().observe(this, BaseObserver {
            it.toast(this)
        })

        viewModelUpdate.onSuccess().observe(this, BaseObserver {
            it.message?.toast(this)
            setResult(Activity.RESULT_OK)
            finish()
        })
    }


    private fun initView() {
        tvCompany.asButton()
        tvServices.asButton()
        tvContactPerson.asButton()

        tilContact.visibility = View.GONE
    }

    private fun initData() {
        tvPriceTotal.text = 0.toIdr()

        // login As Company
        val loginResponse = PrefsUtil.getLoginResponse() ?: return
        if (!Constant.isLoginAsMitraOrSales()) {
            selectedCompany =
                    CompanyResponse(companyId = loginResponse.companyId, companyName = loginResponse.companyName)
            tvCompany.setText(selectedCompany?.companyName)
            tvCompany.isEnabled = false
        }
    }

    private fun initPickedServices() {
        mAdapter = OrderAddServicesAdapter(true, true) { response, position, isDelete, isEdit ->
            if (isDelete) {
                list.removeAt(position)
                mAdapter?.remove(response, position)
                countPrice()
            }

            if (isEdit) {
                val dialog = OrderEditDialog.newInstance(response)
                dialog.setListener(object : OrderEditDialog.OnDialogListener {
                    override fun onSubmit(response: ServiceCorporateResponse) {
                        list.removeAt(position)
                        list.add(position, response)
                        mAdapter?.replace(position, response)
                    }
                })

                try {
                    if (dialog.isAdded) dialog.dismissAllowingStateLoss()
                    dialog.show(supportFragmentManager, "")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        rv.apply {
            layoutManager = LinearLayoutManager(this@OrderAddActivity)
            adapter = mAdapter
        }
    }

    private fun onClickListener() {
        tvCompany.setOnClickListener(this)
        tvServices.setOnClickListener(this)
        tvContactPerson.setOnClickListener(this)

        setLoadingButton(R.id.include_button_create, R.string.order_add) {
            if (selectedCompany == null) {
                getString(R.string.choose_company_mandatory).toast(this)
                return@setLoadingButton
            }

            if (Constant.isLoginAsMitraOrSales()) {
                if (selectedContact == null) {
                    getString(R.string.choose_contact_person_mandatory).toast(this)
                    return@setLoadingButton
                }
            }

            if (list.isEmpty()) {
                getString(R.string.choose_service_mandatory).toast(this)
                return@setLoadingButton
            }

            viewModelCreate.postOrderCreate(
                OrderCreateRequest(
                    selectedCompany?.companyId.toString(),
                    selectedContact?.cpId.toString(),
                    list.mapTo(ArrayList()) {
                        OrderServices(it.serviceId, it.serviceNodes, it.serviceNotes)
                    },
                    etNotes.text.toString().trim().replaceNewline()
                )
            )
        }

        setLoadingButtonSecondary(R.id.include_button_edit, R.string.order_save) {
            if (list.isEmpty()) {
                getString(R.string.choose_service_mandatory).toast(this)
                return@setLoadingButtonSecondary
            }

            viewModelUpdate.postOrderUpdate(
                OrderUpdateRequest(
                    companyId = selectedCompany?.companyId,
                    cpId = selectedContact?.cpId,
                    orderId = transactionResponse?.orderId,
                    orderService = list.mapTo(ArrayList()) {
                        OrderServices(it.serviceId, it.serviceNodes, it.serviceNotes)
                    },
                    notes = etNotes.text.toString().trim().replaceNewline()
                )
            )
        }
    }

    private fun showReason() {
        if (orderCancelDialog == null) {
            orderCancelDialog = OrderCancelDialog()
            orderCancelDialog?.setOnListener(object : OrderCancelDialog.OnListener {
                override fun onSubmit(reason: String) {
                    if (reason.isEmpty()) {
                        getString(R.string.reason_mandatory).toast(applicationContext)
                    } else {
                        val request = OrderCancelRequest(transactionResponse?.orderId, reason)
                        viewModelDelete.postOrderCancel(request)
                    }
                }
            })
        }

        try {
            if (orderCancelDialog?.isVisible == true) orderCancelDialog?.dismissAllowingStateLoss()
            orderCancelDialog?.show(supportFragmentManager, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.let {
            it.title = resources.getString(R.string.menu_order)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constant.ActivityResult.RC_SEARCH_SERVICE -> {
                    data?.let {
                        val item = it.getParcelableExtra<ServiceCorporateResponse>(SearchServiceActivity.BUNDLE)

                        if (list.any { i -> i.serviceId == item.serviceId }) {
                            getString(R.string.service_already_selected, item.serviceRegulation).toast(this)
                            return@let
                        }

                        insertServices(item)
                    }
                }

                Constant.ActivityResult.RC_SEARCH_CLIENT -> {
                    data?.let {
                        setupCompany(it.getParcelableExtra(SearchClientActivity.BUNDLE))
                    }
                }

                Constant.ActivityResult.RC_SEARCH_CONTACT_PERSON -> {
                    data?.let {
                        setupCompanyContact(it.getParcelableExtra(ContactPersonListActivity.BUNDLE))
                    }
                }
            }
        }
    }

    private fun setupCompanyContact(contactPersonResponse: ContactPersonResponse) {
        selectedContact = contactPersonResponse
        tvContactPerson.setText(selectedContact?.cpName)
    }

    private fun setupCompany(companyResponse: CompanyResponse) {
        selectedCompany = companyResponse
        tvCompany.setText(selectedCompany?.companyName)
        tilContact.visibility = View.VISIBLE
        selectedContact = null
        tvContactPerson.setText("")
    }

    private fun insertServices(item: ServiceCorporateResponse?) {
        if (item == null) return
        list.add(item)
        mAdapter?.add(item)
        countPrice()
    }

    private fun countPrice() {
        var total = 0
        list.forEach { response -> total += response.servicePrice ?: 0 }
        tvPriceTotal.text = total.toIdr()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (intent.hasExtra(BUNDLE_EDIT)) menuInflater.inflate(R.menu.order_cancel, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.order_cancel -> {
                showReason()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}