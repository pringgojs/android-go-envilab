package com.envilabindonesia.android.ui.order.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.view.FullBottomSheetDialogFragment
import com.envilabindonesia.android.data.response.ServiceCorporateResponse
import kotlinx.android.synthetic.main.dialog_order_edit.*

/**
 * Created by galihadityo on 2019-05-25.
 */

class OrderEditDialog : FullBottomSheetDialogFragment(), View.OnClickListener {

    companion object {
        const val BUNDLE = "OrderEditDialog.BUNDLE"
        fun newInstance(response: ServiceCorporateResponse): OrderEditDialog {
            val fragment = OrderEditDialog()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE, response)
            }
            return fragment
        }
    }

    private var response: ServiceCorporateResponse? = null

    interface OnDialogListener {
        fun onSubmit(response: ServiceCorporateResponse)
    }

    private var onDialogListener: OnDialogListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_order_edit, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        response = arguments?.getParcelable(BUNDLE)

        tvTitle.text = response?.catName
        tvSubtitle.text = response?.serviceParameter
        tvTotal.text = response?.serviceNodes.toString()
        etNotes.setText(response?.serviceNotes)

        visibilityButtonPlusMinus()
        tvTotal.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                visibilityButtonPlusMinus()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                visibilityButtonPlusMinus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        btnMin.setOnClickListener(this)
        btnPlus.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnMin -> tvTotal.text = tvTotal.text.toString().toInt().minus(1).toString()
            btnPlus -> tvTotal.text = tvTotal.text.toString().toInt().plus(1).toString()
            btnSubmit -> {
                response?.serviceNodes = tvTotal.text.toString().toInt()
                response?.serviceNotes = etNotes.text.toString().trim()
                onDialogListener?.onSubmit(response ?: return)
                dismissAllowingStateLoss()
            }
        }
    }

    private fun visibilityButtonPlusMinus() {
        if (tvTotal.text.toString() == "1") {
            btnMin.visibility = View.INVISIBLE
        } else {
            btnMin.visibility = View.VISIBLE
        }
    }

    fun setListener(onDialogListener: OnDialogListener) {
        this.onDialogListener = onDialogListener
    }

}