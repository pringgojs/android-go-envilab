package com.envilabindonesia.android.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolder
import com.envilabindonesia.android.data.response.ScheduleResponse
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.item_schedule_day.view.*

/**
 * Created by galihadityo on 2019-05-21.
 */

class ScheduleAdapter: BaseViewHolder<ScheduleResponse, ScheduleAdapter.ViewHolder>() {

    override fun bindView(
        holder: ViewHolder,
        data: BaseAdapterModel<ScheduleResponse>,
        onClick: (ScheduleResponse) -> Unit
    ) {
        val response = data.data ?: return
        with (holder.itemView) {
            if (response.isDayOff == true) {
                dayColorRed(tvDay)
            } else {
                tvDay?.setTextColor(ContextCompat.getColor(tvDay.context, R.color.textview_black))
            }

            if (response.date?.isNotEmpty() == true) {
                if (TimeUtil.getHumanDay(this.context, response.date) == this.context.getString(R.string.day_sunday)) {
                    dayColorRed(tvDay)
                }
            }

            if (response.date?.isNotEmpty() == true) {
                tvDay.text = response.date.takeLast(2).toInt().toString()
            } else {
                tvDay.text = ""
            }

            if (response.orderFound ?: 0 > 0) {
                tvSchedule.text = response.orderFound.toString()
                tvSchedule.visibility = View.VISIBLE
            } else {
                tvSchedule.visibility = View.INVISIBLE
            }

            btnSchedule.setOnClickListener { onClick(response) }
        }
    }

    private fun dayColorRed(tvDay: AppCompatTextView?) {
        tvDay?.setTextColor(ContextCompat.getColor(tvDay.context, R.color.button_red))
    }

    override fun createViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule_day, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}