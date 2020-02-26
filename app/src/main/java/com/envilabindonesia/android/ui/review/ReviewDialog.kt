package com.envilabindonesia.android.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.request.ReviewRequest
import com.envilabindonesia.android.data.response.Rate
import com.envilabindonesia.android.data.response.TransactionResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_review.*

/**
 * Created by galihadityo on 2019-05-25.
 */

class ReviewDialog : BottomSheetDialogFragment() {

    companion object {
        const val BUNDLE = "ReviewDialog.BUNDLE"
        const val BUNDLE_RATE = "ReviewDialog.BUNDLE_RATE"
        fun newInstance(rate: Rate, transactionResponse: TransactionResponse): ReviewDialog {
            val fragment = ReviewDialog()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_RATE, rate)
                putParcelable(BUNDLE, transactionResponse)
            }
            return fragment
        }
    }

    interface OnReviewDialogListener {
        fun onSubmit(reviewRequest: ReviewRequest)
    }

    private var onReviewDialogListener: OnReviewDialogListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rate = arguments?.getParcelable<Rate>(BUNDLE_RATE)
        val transaction = arguments?.getParcelable<TransactionResponse>(BUNDLE)

        tvServices.text = rate?.serviceRegulation ?: "null"

        ratingBar.setOnTouchListener { v, event ->
            tvRating.text = activity?.let { Constant.Review.getStatus(it, ratingBar.rating) }
            return@setOnTouchListener ratingBar.onTouchEvent(event)
        }

        btnSubmit.setOnClickListener {
            onReviewDialogListener?.onSubmit(
                ReviewRequest(
                    orderId = transaction?.orderId,
                    serviceId = rate?.serviceId,
                    comment = etReason.text.toString().trim(),
                    rating = ratingBar.rating.toInt()
                )
            )
            dismissAllowingStateLoss()
        }

        if (rate?.rated == true) {
            ratingBar.rating = rate.rate?.toFloat() ?: 0f
            ratingBar.setOnTouchListener(null)
            etReason.setText(rate.rateComment ?: "")
            btnSubmit.visibility = View.INVISIBLE
        }
    }

    fun setListener(onReviewDialogListener: OnReviewDialogListener) {
        this.onReviewDialogListener = onReviewDialogListener
    }

}