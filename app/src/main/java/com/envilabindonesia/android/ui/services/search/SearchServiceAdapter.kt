package com.envilabindonesia.android.ui.services.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.ServiceCorporateResponse
import com.envilabindonesia.android.extension.showCurrency
import com.envilabindonesia.android.extension.toIdr
import kotlinx.android.synthetic.main.item_search_service.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class SearchServiceAdapter: BaseViewHolderLoadmore<ServiceCorporateResponse, SearchServiceAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<ServiceCorporateResponse>,
        onClick: (ServiceCorporateResponse) -> Unit
    ) {
        with (holder.itemView) {
            tvName.text = data.data?.catName
            tvRegulation.text = data.data?.serviceRegulation
            tvParameter.text = data.data?.serviceParameter
            tvPrice.text = data.data?.servicePrice?.toIdr() ?: "null"
            tvPrice.showCurrency()
            layout.setOnClickListener {
                data.data?.let { it1 -> onClick(it1) }
            }
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_service, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}