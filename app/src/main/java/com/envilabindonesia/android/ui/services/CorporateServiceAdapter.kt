package com.envilabindonesia.android.ui.services

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
import kotlinx.android.synthetic.main.item_corporate_service.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class CorporateServiceAdapter: BaseViewHolderLoadmore<ServiceCorporateResponse, CorporateServiceAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<ServiceCorporateResponse>,
        onClick: (ServiceCorporateResponse) -> Unit
    ) {
        holder.itemView.tvRegulation.text = data.data?.serviceRegulation
        holder.itemView.tvParameter.text = data.data?.serviceParameter
        holder.itemView.tvPrice.text = data.data?.servicePrice?.toIdr() ?: ""
//        holder.itemView.btnItem.setOnClickListener {
//            if (holder.itemView.layoutDetail.isVisible) {
//                holder.itemView.imgArrow.setImageResource(R.drawable.ic_arrow_down)
//                holder.itemView.layoutDetail.visibility = View.GONE
//            } else {
//                holder.itemView.imgArrow.setImageResource(R.drawable.ic_arrow_up)
//                holder.itemView.layoutDetail.visibility = View.VISIBLE
//            }
//            holder.itemView.imgArrow.invalidate()
//        }

        holder.itemView.tvPrice.showCurrency()
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_corporate_service, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}