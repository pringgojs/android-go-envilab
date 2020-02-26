package com.envilabindonesia.android.ui.performance

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.response.PerformanceResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toIdr
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.util.PrefsUtil
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_performance.*
import kotlinx.android.synthetic.main.item_chart_legend.view.*
import kotlinx.android.synthetic.main.layout_chart.view.*
import kotlinx.android.synthetic.main.layout_chart_bar.view.*
import kotlinx.android.synthetic.main.layout_chart_line.view.*
import javax.inject.Inject


/**
 * Created by galihadityo on 2019-03-11.
 */

class PerformanceFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var response: PerformanceResponse? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<PerformanceViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_performance

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_performance)
        observer()
        swipe.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onRefresh() {
        viewModel.getPerformance()
    }

    private fun observer() {
        viewModel.onSuccess().observe(this, BaseObserver {
            response = it.result
            header()
            performance()
            pieChart()
            barChart()
            lineChart()
        })

        viewModel.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })

        viewModel.onLoading().observe(this, BaseObserver {
            swipe.isRefreshing = it
        })
    }

    private fun lineChart() {
        if (response == null) return
        if (response?.monthlyOrders == null) return

        val numberData = response?.monthlyOrders?.chartStats?.size
        if (numberData == null || numberData == 0) return

        includeChartLine.visibility = if (response?.monthlyOrders?.show == true) View.VISIBLE else View.GONE

        if (!includeChartLine.isVisible) return

        with(includeChartLine) {
            chartLine.legend.isEnabled = false
            chartLine.description.isEnabled = false

            chartLine.setTouchEnabled(false)

            chartLine.setPinchZoom(false)
            chartLine.setDrawGridBackground(false)

            chartLine.axisLeft.setDrawGridLines(false)
            chartLine.axisLeft.isEnabled = false

            chartLine.axisRight.setDrawGridLines(false)
            chartLine.axisRight.isEnabled = false

            // set label x to bottom
            chartLine.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chartLine.xAxis.setDrawGridLines(false)
            chartLine.xAxis.granularity = 1f
            chartLine.xAxis.isGranularityEnabled = true

            chartLine.minOffset = 30f

            // disable zoom
            chartLine.setScaleEnabled(false)

            chartLine.animateY(1000)
        }

        val values: ArrayList<Entry> = arrayListOf()
        response?.monthlyOrders?.chartStats?.forEachIndexed { index, it ->
            val entry = Entry(index.plus(1).toFloat(), it.monthlyTotal?.toFloat() ?: 0f)
            values.add(entry)
        }

        val data = LineDataSet(values, "")
        data.values = values
        data.valueTextSize = 8f
        data.valueFormatter =
                IValueFormatter { value, _, _, _ -> value.toInt().toIdr().removePrefix("Rp ") }
        activity?.let {
            data.valueTextColor = ContextCompat.getColor(it, R.color.textview_gray)
        }


        val dataSet: ArrayList<ILineDataSet> = arrayListOf()
        dataSet.add(data)

        val barData = LineData(dataSet)
        with(includeChartLine) {
            chartLine.data = barData
            chartLine.xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                response?.monthlyOrders?.chartStats?.get(value.toInt().minus(1))?.label ?: ""
            }
            chartLine.data.notifyDataChanged()
            chartLine.notifyDataSetChanged()
        }
    }

    private fun barChart() {
        if (response == null) return
        if (response?.weeklyOrders == null) return
        if (response?.weeklyOrders?.chartStats == null || response?.weeklyOrders?.chartStats?.size == 0) return

        includeChartBar.visibility = if (response?.weeklyOrders?.show == true) View.VISIBLE else View.GONE

        if (!includeChartBar.isVisible) return

        with(includeChartBar) {
            chartBar.legend.isEnabled = false
            chartBar.description.isEnabled = false

            chartBar.setPinchZoom(false)
            chartBar.setDrawGridBackground(false)

            chartBar.setDrawBarShadow(false)
            chartBar.setDrawValueAboveBar(true)

            chartBar.axisLeft.setDrawGridLines(false)
            chartBar.axisLeft.isEnabled = false

            chartBar.axisRight.setDrawGridLines(false)
            chartBar.axisRight.isEnabled = false

            // set label x to bottom
            chartBar.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chartBar.xAxis.setDrawGridLines(false)

            // disable zoom
            chartBar.setScaleEnabled(false)

            chartBar.setFitBars(true)
            chartBar.setTouchEnabled(false)

            chartBar.animateY(1000)
        }

        val values: ArrayList<BarEntry> = arrayListOf()
        response?.weeklyOrders?.chartStats?.forEachIndexed { index, it ->
            val entry = BarEntry(index.plus(1).toFloat(), it.weeklyTotal?.toFloat() ?: 0f)
            values.add(entry)
        }

        val data = BarDataSet(values, "")
        data.values = values
        data.valueTextSize = 8f
        data.valueFormatter =
                IValueFormatter { value, _, _, _ -> value.toInt().toIdr().removePrefix("Rp ") }
        activity?.let {
            data.valueTextColor = ContextCompat.getColor(it, R.color.textview_gray)
        }

        val dataSet: ArrayList<IBarDataSet> = arrayListOf()
        dataSet.add(data)

        val barData = BarData(dataSet)
        with(includeChartBar) {
            chartBar.data = barData
            chartBar.data.notifyDataChanged()
            chartBar.notifyDataSetChanged()
        }
    }

    private fun header() {
        tvMonth.text = response?.months?.requestedMonth?.label ?: ""
        tvTotal.text = response?.totalOrders?.totalAmount?.toIdr() ?: "0"
    }

    private fun performance() {
        val submit = response?.submittedOrders
        val complete = response?.completedOrders
        val revenue = response?.potentialRevenue

        tvOrderSubmited.text = submit?.orders.toString()
        layoutOrderSubmit.visibility = if (submit?.show == true) View.VISIBLE else View.GONE

        tvOrderCompleted.text = complete?.orders.toString()
        layoutOrderComplete.visibility = if (complete?.show == true) View.VISIBLE else View.GONE

        tvLabelRevenue.text = revenue?.tearingCommissionLabel ?: ""
        tvOrderRevenue.text = revenue?.totalAmount?.toIdr() ?: getString(R.string.dash)
        layoutOrderRevenue.visibility = if (revenue?.show == true) View.VISIBLE else View.GONE
    }

    @Suppress("DEPRECATION")
    private fun pieChart() {
        if (response == null) return
        if (response?.orderChart == null) return
        if (response?.orderChart?.chartStats == null) return
        if (response?.orderChart?.chartStats?.size == 0) return

        val dataChart = response?.orderChart
        includeChart.visibility = if (dataChart?.show == true) View.VISIBLE else View.GONE

        if (!includeChart.isVisible) return

        val listPieChart = arrayListOf<PieEntry>()
        val listPieColor = arrayListOf<Int>()

        dataChart?.chartStats?.forEach {
            listPieChart.add(PieEntry(it.ordersFoundPercentage?.toFloat() ?: 0f, ""))
            listPieColor.add(Color.parseColor(PrefsUtil.getTransactionStatusColor(it.statusId ?: 0)))
        }

        val dataset = PieDataSet(listPieChart, "")
        dataset.colors = listPieColor
        dataset.valueTextColor = Color.WHITE

        val data = PieData(dataset)
        data.setValueFormatter(DefaultValueFormatter(0))

        includeChart.chart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            isRotationEnabled = false
            setTouchEnabled(false)
            setUsePercentValues(true)
            animateY(1000, Easing.EasingOption.EaseInOutQuad)
            setData(data)
        }

        chartLegend()
    }

    private fun chartLegend() {
        includeChart.listLegend.removeAllViews()
        response?.orderChart?.chartStats?.forEach {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_chart_legend, null)
            view.tvBackground.setBackgroundColor(
                Color.parseColor(
                    PrefsUtil.getTransactionStatusColor(
                        it.statusId ?: 0
                    )
                )
            )
            view.tvName.text = it.statusName?.toLowerCase()
            includeChart.listLegend.addView(view)
        }
    }

}