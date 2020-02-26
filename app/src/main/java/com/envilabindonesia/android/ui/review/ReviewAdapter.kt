package com.envilabindonesia.android.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.Rate
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.item_review.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class ReviewAdapter: BaseViewHolder<Rate, ReviewAdapter.ViewHolder>() {

    private val VIEW_RATED: Int = 0
    private val VIEW_UNRATED: Int = 1

    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<Rate>,
        onClick: (Rate) -> Unit
    ) {
        val response = data.data
        with(holder.itemView) {
            tvName.text = response?.catName ?: "null"
            tvRegulation.text = response?.serviceRegulation ?: "null"
            tvParameter.text = response?.serviceParameter ?: "null"
            layout_selected.setOnClickListener {
                data.data?.let { it1 -> onClick(it1) }
            }

            if (response?.rated == true) {
                vf.displayedChild = VIEW_RATED
                ratingBar.rating = response.rate?.toFloat() ?: 0f
                tvDateRating.text = TimeUtil.getHumanUTC(response.rateDate ?: "null")
                tvComment.text = response.rateComment
                tvUserRating.text = response.userFullName
            } else {
                vf.displayedChild = VIEW_UNRATED
            }
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_review, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}