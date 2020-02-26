package com.envilabindonesia.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.envilabindonesia.android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_app_version.*

/**
 * Created by galihadityo on 2019-05-25.
 */

class AppVersionDialog : BottomSheetDialogFragment() {

    interface OnListener {
        fun onUpdate()
    }

    private var listener: OnListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_app_version, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvVersion.text = getString(R.string.app_version_update)
        btnUpdate.setOnClickListener {
            dismissAllowingStateLoss()
            listener?.onUpdate()
        }
    }

    fun setOnListener(onListener: OnListener) {
        listener = onListener
    }

}