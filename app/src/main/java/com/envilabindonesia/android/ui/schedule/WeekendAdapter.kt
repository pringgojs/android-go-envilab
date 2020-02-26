package com.envilabindonesia.android.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.ScheduleResponse
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.item_weekend.view.*
import java.util.*

/**
 * Created by galihadityo on 2019-05-21.
 */

class WeekendAdapter: BaseViewHolder<ScheduleResponse, WeekendAdapter.ViewHolder>() {

    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<ScheduleResponse>,
        onClick: (ScheduleResponse) -> Unit
    ) {
        val response = data.data ?: return
        with (holder.itemView) {
            val date = response.date

            val st = StringTokenizer(date, "-")
            val year = st.nextToken()
            val month = st.nextToken()
            val day = st.nextToken().toInt()

            val monthName = TimeUtil.getHumanMonth(tvDay.context, month.toInt())
            tvDay.text = String.format("$day $monthName")
            tvDayName.text = response.dayOff
        }
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weekend, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}