package com.envilabindonesia.android.base.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by galihadityo on 2019-06-15.
 */

open class FullBottomSheetDialogFragment: BottomSheetDialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // full screen bottom sheet dialog
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val bottomSheetFrame = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val coordinatorLayout = bottomSheetFrame?.parent as CoordinatorLayout
            val bottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheetFrame)
            bottomSheetBehavior.peekHeight = bottomSheetFrame.height
            coordinatorLayout.parent.requestLayout()
        }
    }

}