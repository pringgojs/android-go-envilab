package com.envilabindonesia.android.ui.client.contact.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.ContactPersonResponse
import kotlinx.android.synthetic.main.item_contact_person.view.*

/**
 * Created by galihadityo on 2019-04-16.
 */

class ContactPersonAdapter: BaseViewHolder<ContactPersonResponse, ContactPersonAdapter.ViewHolder>() {
    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<ContactPersonResponse>,
        onClick: (ContactPersonResponse) -> Unit
    ) {
        holder.itemView.tvName.text = data.data?.cpName
        holder.itemView.tvAddress.text = data.data?.cpPosition
        holder.itemView.tvPhone.text = data.data?.cpPhone
        holder.itemView.tvEmail.text = data.data?.cpEmail
        holder.itemView.layout_selected_company.setOnClickListener {
            data.data?.let { it1 -> onClick(it1) }
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_contact_person, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}