package com.envilabindonesia.android.ui.client

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.CompanyResponse
import kotlinx.android.synthetic.main.item_client.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class ClientAdapter: BaseViewHolderLoadmore<CompanyResponse, ClientAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<CompanyResponse>,
        onClick: (CompanyResponse) -> Unit
    ) {
        holder.itemView.tvName.text = data.data?.companyName
        holder.itemView.tvAddress.text = data.data?.companyAddress
        holder.itemView.layout_selected_company.setOnClickListener {
            data.data?.let { it1 -> onClick(it1) }
        }

        if (data.data?.isProperColorSet == true) {
            if (!data.data.properColor.isNullOrEmpty()) {
                holder.itemView.label.visibility = View.VISIBLE
                holder.itemView.label.setTriangleBackgroundColor(Color.parseColor(data.data.properColor))
                holder.itemView.label.primaryText = data.data.properLabel
            } else {
                holder.itemView.label.visibility = View.INVISIBLE
            }
        } else {
            holder.itemView.label.visibility = View.INVISIBLE
        }

    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_client, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}