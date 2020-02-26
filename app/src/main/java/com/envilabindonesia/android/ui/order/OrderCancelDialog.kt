package com.envilabindonesia.android.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.envilabindonesia.android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_order_cancel.*

/**
 * Created by galihadityo on 2019-05-25.
 */

class OrderCancelDialog : BottomSheetDialogFragment() {

    interface OnListener {
        fun onSubmit(reason: String)
    }

    private var listener: OnListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_order_cancel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSubmit.setOnClickListener {
            dismissAllowingStateLoss()
            listener?.onSubmit(etReason.text.toString().trim())
        }
    }

    fun setOnListener(onListener: OnListener) {
        listener = onListener
    }

}