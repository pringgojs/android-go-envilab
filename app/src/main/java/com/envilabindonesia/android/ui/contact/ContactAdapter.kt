package com.envilabindonesia.android.ui.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.ContactSupportResponse
import com.envilabindonesia.android.data.response.FAQResponse
import kotlinx.android.synthetic.main.item_base_adapter.view.*
import kotlinx.android.synthetic.main.item_faq.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class ContactAdapter: BaseViewHolder<ContactSupportResponse, ContactAdapter.ViewHolder>() {

    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<ContactSupportResponse>,
        onClick: (ContactSupportResponse) -> Unit
    ) {
        holder.itemView.tv.text = data.data?.branchname
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