package com.envilabindonesia.android.ui.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.TrainingMaterialResponse
import kotlinx.android.synthetic.main.item_base_adapter.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class TrainingPdfAdapter: BaseViewHolder<TrainingMaterialResponse, TrainingPdfAdapter.ViewHolder>() {

    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<TrainingMaterialResponse>,
        onClick: (TrainingMaterialResponse) -> Unit
    ) {
        holder.itemView.tv.text = data.data?.title
        data.iconLeft?.let { holder.itemView.ivIconLeft.setImageResource(it) }
        holder.itemView.layout.setOnClickListener {
            data.data?.let { it1 -> onClick(it1) }
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_base_adapter, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}