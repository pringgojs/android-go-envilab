package com.envilabindonesia.android.ui.schedule.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.extension.showCurrency
import com.envilabindonesia.android.extension.toIdr
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.item_sampling.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class ScheduleSamplingAdapter: BaseViewHolder<TransactionResponse, ScheduleSamplingAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<TransactionResponse>,
        onClick: (TransactionResponse) -> Unit
    ) {
        val transaction = data.data ?: return
        with (holder.itemView) {
            tvDate.text = TimeUtil.getHumanUTC(transaction.created ?: "")
            tvNumber.text = transaction.code
            tvName.text = transaction.companyName
            tvCity.text = transaction.companyAddress
            tvPrice.text = transaction.totalPrice?.toIdr()
            tvPrice.showCurrency()
            layout.setOnClickListener {
                onClick(transaction)
            }
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sampling, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}