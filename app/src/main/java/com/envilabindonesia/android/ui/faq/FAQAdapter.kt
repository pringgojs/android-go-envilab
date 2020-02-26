package com.envilabindonesia.android.ui.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.FAQResponse
import com.envilabindonesia.android.extension.fromHtml
import kotlinx.android.synthetic.main.item_faq.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class FAQAdapter: BaseViewHolder<FAQResponse, FAQAdapter.ViewHolder>() {

    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<FAQResponse>,
        onClick: (FAQResponse) -> Unit
    ) {
        holder.itemView.tvQuestion.text = data.data?.question
        holder.itemView.tvAnswer.text = data.data?.answer?.fromHtml() ?: ""
        holder.itemView.btnItem.setOnClickListener {
            if (holder.itemView.tvAnswer.isVisible) {
                holder.itemView.tvAnswer.visibility = View.GONE
                holder.itemView.imgArrow.setImageResource(R.drawable.ic_arrow_down)
            } else {
                holder.itemView.tvAnswer.visibility = View.VISIBLE
                holder.itemView.imgArrow.setImageResource(R.drawable.ic_arrow_up)
            }
            holder.itemView.imgArrow.invalidate()
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_faq, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}