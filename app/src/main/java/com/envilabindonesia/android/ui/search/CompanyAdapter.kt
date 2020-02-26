package com.envilabindonesia.android.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.CompanyResponse
import kotlinx.android.synthetic.main.item_base_adapter.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class CompanyAdapter: BaseViewHolderLoadmore<CompanyResponse, CompanyAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<CompanyResponse>,
        onClick: (CompanyResponse) -> Unit
    ) {
        holder.itemView.tv.text = data.title
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_base_adapter, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}