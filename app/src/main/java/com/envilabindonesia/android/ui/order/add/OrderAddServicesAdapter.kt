package com.envilabindonesia.android.ui.order.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.response.ServiceCorporateResponse
import com.envilabindonesia.android.extension.showCurrency
import com.envilabindonesia.android.extension.toIdr
import kotlinx.android.synthetic.main.item_picked_services.view.*

/**
 * Created by galihadityo on 2019-03-13.
 */

class OrderAddServicesAdapter(private val hasDelete: Boolean, private val hasEdit: Boolean, val onClick: (ServiceCorporateResponse, Int, Boolean, Boolean) -> Unit):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<ServiceCorporateResponse> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.item_picked_services, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with (holder as ViewHolder) {
            bind(list[position], onClick, position, hasDelete, hasEdit)
        }
    }

    fun replace(position: Int, pickedServicesResponse: ServiceCorporateResponse) {
        list.removeAt(position)
        list.add(position, pickedServicesResponse)
        notifyDataSetChanged()
    }

    fun add(pickedServicesResponse: ServiceCorporateResponse) {
        list.add(pickedServicesResponse)
        notifyDataSetChanged()
    }

    fun remove(pickedServicesResponse: ServiceCorporateResponse, position: Int) {
        list.remove(pickedServicesResponse)
        notifyDataSetChanged()
    }

    class ViewHolder(val context: Context, val view: View): RecyclerView.ViewHolder(view) {

        fun bind(
            response: ServiceCorporateResponse,
            onClick: (ServiceCorporateResponse, Int, Boolean, Boolean) -> Unit,
            position: Int,
            hasDelete: Boolean,
            hasEdit: Boolean
        ) {
            view.tvName.text = response.catName ?: "null"
            view.tvRegulation.text = response.serviceRegulation ?: "null"
            view.tvParameter.text = response.serviceParameter ?: "null"

            response.serviceNodes?.let {
                view.tvTotal.text = it.toString()
            }

            view.tvPrice.text = response.servicePrice?.toIdr() ?: "null"

            if (response.serviceNotes != null && response.serviceNotes?.trim()?.isNotEmpty() == true) {
                view.layout_note.visibility = View.VISIBLE
                view.tvNotes.text = response.serviceNotes
            } else {
                view.layout_note.visibility = View.GONE
            }

            view.imgDelete.setOnClickListener { onClick(response, position, true, false) }
            view.imgEdit.setOnClickListener { onClick(response, position, false, true) }

            view.imgDelete.visibility = if (hasDelete) View.VISIBLE else View.INVISIBLE
            view.imgEdit.visibility = if (hasEdit) View.VISIBLE else View.INVISIBLE

            view.tvPrice.showCurrency()
        }

    }

}