package com.envilabindonesia.android.ui.order.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.response.InvoiceData
import com.envilabindonesia.android.data.response.QuotationData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_send_document.*

/**
 * Created by galihadityo on 2019-05-25.
 */

class SendDocumentDialog : BottomSheetDialogFragment() {

    companion object {
        const val BUNDLE_QUOTATION = "SendDocumentDialog.BUNDLE.QUOTATION"
        const val BUNDLE_INVOICE = "SendDocumentDialog.BUNDLE.INVOICE"

        fun quotation(quotationData: QuotationData): SendDocumentDialog {
            val fragment = SendDocumentDialog()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_QUOTATION, quotationData)
            }
            return fragment
        }

        fun invoice(invoiceData: InvoiceData): SendDocumentDialog {
            val fragment = SendDocumentDialog()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_INVOICE, invoiceData)
            }
            return fragment
        }
    }

    interface OnListener {
        fun onSendInvoice(email: String, invoiceData: InvoiceData)
        fun onSendQuotation(email: String, quotationData: QuotationData)
    }

    private var listener: OnListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_send_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<QuotationData>(BUNDLE_QUOTATION)?.let { data ->
            tvTitle.text = getString(R.string.quotation)
            etEmail.setText(data.defaultEmail)
            etNumber.setText(data.number)
            btnSend.setOnClickListener {
                dismissAllowingStateLoss()
                listener?.onSendQuotation(etEmail.text.toString().trim() , data)
            }
        }

        arguments?.getParcelable<InvoiceData>(BUNDLE_INVOICE)?.let { data ->
            tvTitle.text = getString(R.string.invoice)
            etEmail.setText(data.defaultEmail)
            etNumber.setText(data.number)
            btnSend.setOnClickListener {
                dismissAllowingStateLoss()
                listener?.onSendInvoice(etEmail.text.toString().trim() , data)
            }
        }
    }

    fun setOnListener(onListener: OnListener) {
        listener = onListener
    }

}