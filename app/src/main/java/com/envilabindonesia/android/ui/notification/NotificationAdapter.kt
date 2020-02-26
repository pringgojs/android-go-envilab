package com.envilabindonesia.android.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.MyInboxResponse
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.item_notification.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class NotificationAdapter: BaseViewHolderLoadmore<MyInboxResponse, NotificationAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<MyInboxResponse>,
        onClick: (MyInboxResponse) -> Unit
    ) {
        val tvTitle = holder.itemView.tv
        tvTitle.text = data.data?.message

        holder.itemView.tvDate.text = TimeUtil.getHumanUTC(data.data?.created ?: "")
        holder.itemView.layout.setOnClickListener {
            data.data?.let { it1 -> onClick(it1) }
        }

        if (data.data?.isRead == true) {
            tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.textview_gray))
        } else {
            tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.textview_black))
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}