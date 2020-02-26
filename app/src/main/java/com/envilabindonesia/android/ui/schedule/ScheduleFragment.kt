package com.envilabindonesia.android.ui.schedule

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapter
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.data.response.ScheduleResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.schedule.list.ScheduleSamplingListActivity
import com.envilabindonesia.android.util.TimeUtil
import kotlinx.android.synthetic.main.fragment_schedule.*
import javax.inject.Inject


/**
 * Created by galihadityo on 2019-04-20.
 */

class ScheduleFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            btnMonthPrev -> {
                currentMonth--
                onRefresh()
            }

            btnMonthNext -> {
                currentMonth++
                onRefresh()
            }
        }
    }

    override fun onRefresh() {
        mAdapterCalendar?.clear()
        mAdapterWeekend?.clear()

        val monthYear = TimeUtil.getMonthYear(currentMonth)
        year = monthYear.substringBefore("-", "").toInt()
        month = monthYear.substringAfter("-", "").toInt()

        tvMonth.text = activity?.let { TimeUtil.getHumanMonth(it, month ?: 0) }
        tvYear.text = year.toString()

        viewModel.getSchedule(month ?: return, year ?: return)
    }

    private var mAdapterCalendar: BaseAdapter<ScheduleResponse, ScheduleAdapter.ViewHolder>? = null
    private var mAdapterWeekend: BaseAdapter<ScheduleResponse, WeekendAdapter.ViewHolder>? = null

    companion object {
        const val TOTAL_DAYS = 7
    }

    private var currentMonth: Int = 0
    private var month: Int? = null
    private var year: Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<ScheduleViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_schedule

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_schedule)

        swipe.setOnRefreshListener(this)
        setupRvCalendar()
        setupRvWeekend()
        observe()
        onClickListener()
        onRefresh()
    }

    private fun onClickListener() {
        btnMonthPrev.setOnClickListener(this)
        btnMonthNext.setOnClickListener(this)
    }

    private fun setupRvWeekend() {
        mAdapterWeekend = BaseAdapter(WeekendAdapter()) {

        }

        rvWeekend.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = mAdapterWeekend
        }
    }

    private fun setupRvCalendar() {
        mAdapterCalendar = BaseAdapter(ScheduleAdapter()) {

            if (it.orders != null && it.orders.isEmpty()) {
                activity?.let { it1 -> getString(R.string.no_sampling_schedule).toast(it1) }
                return@BaseAdapter
            }

            activity?.let { it1 ->
                val day = it.date?.takeLast(2) ?: "-"
                val month = tvMonth.text.toString()
                val year = tvYear.text.toString()

                ScheduleSamplingListActivity.start(
                    it1,
                    ArrayList(it.orders),
                    getString(R.string.sampling_schedule_periode, "$day $month $year")
                )
            }

        }

        rv.apply {
            layoutManager = GridLayoutManager(activity, TOTAL_DAYS)
            setHasFixedSize(true)
            adapter = mAdapterCalendar
        }
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver {
            swipe.isRefreshing = it
        })

        viewModel.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })

        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<ScheduleResponse>> =
                it.result?.map { datas -> BaseAdapterModel(datas.date, datas) }
                    ?.toCollection(ArrayList())
                    ?: arrayListOf()

            setupCalendar(list)
            setupWeekend(list)
        })
    }

    private fun setupWeekend(list: ArrayList<BaseAdapterModel<ScheduleResponse>>) {
        mAdapterWeekend?.addAll(
            list.filter {
                it.data?.isDayOff == true
            } as ArrayList<BaseAdapterModel<ScheduleResponse>>
        )
    }

    private fun setupCalendar(list: ArrayList<BaseAdapterModel<ScheduleResponse>>) {
        var totalEmpty = 0
        list.first().data?.date?.let { it1 ->
            activity?.let { it2 ->
                val day = TimeUtil.getHumanDay(it2, it1)
                totalEmpty = when (day) {
                    getString(R.string.day_monday) -> 1
                    getString(R.string.day_tuesday) -> 2
                    getString(R.string.day_wednesday) -> 3
                    getString(R.string.day_thursday) -> 4
                    getString(R.string.day_friday) -> 5
                    getString(R.string.day_saturday) -> 6
                    else -> 0
                }
            }
        }

        val emptyDay = arrayListOf<BaseAdapterModel<ScheduleResponse>>()
        for (i in 0 until totalEmpty) emptyDay.add(BaseAdapterModel("", ScheduleResponse()))

        if (emptyDay.isEmpty()) {
            mAdapterCalendar?.addAll(list)
        } else {
            emptyDay.addAll(list)
            mAdapterCalendar?.addAll(emptyDay)
        }
    }

}