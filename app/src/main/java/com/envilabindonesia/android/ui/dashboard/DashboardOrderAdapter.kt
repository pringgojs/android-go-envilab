package com.envilabindonesia.android.ui.dashboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.response.Preview
import com.envilabindonesia.android.util.PrefsUtil
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.item_dashboard_order.view.*

/**
 * Created by galihadityo on 2019-03-13.
 */

class DashboardOrderAdapter(private val isSubordinate: Boolean, private val onClick: (Preview) -> Unit):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: ArrayList<Preview> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DashboardOrderHolder(parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard_order, parent, false))

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DashboardOrderHolder).bind(mList[position], onClick, isSubordinate)
    }

    class DashboardOrderHolder(val context: Context, val view: View): RecyclerView.ViewHolder(view) {

        fun bind(
            response: Preview,
            onClick: (Preview) -> Unit,
            isSubordinate: Boolean
        ) {
            view.tvDate.text = TimeUtil.getHumanUTC(response.created ?: "")
            view.tvNumber.text = response.code
            view.tvName.text = response.companyName
            view.tvCity.text = response.companyAddress

            with(view.chipStatus) {
                text = response.status?.label ?: "null"
                chipBackgroundColor = ColorStateList.valueOf(
                    Color.parseColor(
                        PrefsUtil.getTransactionStatusColor(response.status?.latest ?: 0)
                    )
                )
            }

            view.setOnClickListener { onClick(response) }

            if (isSubordinate) {
                view.tvCreateBy.text = response.user?.fullName ?: "null"
                view.tvCreateBy.visibility = View.VISIBLE
            } else {
                view.tvCreateBy.text = ""
                view.tvCreateBy.visibility = View.GONE
            }
        }

    }

    fun addAll(listData: ArrayList<Preview>) {
        mList.clear()
        mList.addAll(listData).also { notifyDataSetChanged() }
    }

    fun clear() {
        mList.clear().also { notifyDataSetChanged() }
    }

}