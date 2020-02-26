package com.envilabindonesia.android.ui.order.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.extension.showCurrency
import com.envilabindonesia.android.extension.toCapitalize
import com.envilabindonesia.android.extension.toIdr
import kotlinx.android.synthetic.main.item_order_search.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class OrderSearchAdapter: BaseViewHolderLoadmore<TransactionResponse, OrderSearchAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<TransactionResponse>,
        onClick: (TransactionResponse) -> Unit
    ) {
        val response = data.data ?: return
        with(holder.itemView) {
            tvDate.text = response.created
            tvNumber.text = response.code
            tvName.text = response.companyName
            tvCity.text = response.companyAddress
            tvPrice.text = response.totalPrice?.toIdr() ?: "null"
            tvPrice.showCurrency()
            chipStatus.text = (response.status?.label ?: "null").toCapitalize()
            layout.setOnClickListener {
                onClick(response)
            }
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order_search, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}