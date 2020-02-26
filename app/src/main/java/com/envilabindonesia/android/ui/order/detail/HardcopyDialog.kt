package com.envilabindonesia.android.ui.order.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.response.TestResultResponse
import com.envilabindonesia.android.extension.copyToClipboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_hardcopy.*

/**
 * Created by galihadityo on 2019-05-25.
 */

class HardcopyDialog : BottomSheetDialogFragment() {

    companion object {
        const val BUNDLE = "HardcopyDialog.BUNDLE"
        fun newInstance(testResultResponse: TestResultResponse): HardcopyDialog {
            val fragment = HardcopyDialog()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE, testResultResponse)
            }
            return fragment
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_hardcopy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getParcelable<TestResultResponse>(BUNDLE)

        tvVendor.text = data?.logisticVendor ?: "-"
        tvResi.text = data?.logisticResi ?: "-"
        btnCopy.setOnClickListener {
            tvResi.text.toString().copyToClipboard(activity, getString(R.string.resi_copied))
            dismissAllowingStateLoss()
        }

    }

}