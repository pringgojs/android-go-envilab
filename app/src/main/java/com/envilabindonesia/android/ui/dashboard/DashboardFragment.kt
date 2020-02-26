package com.envilabindonesia.android.ui.dashboard

import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.response.DashboardResponse
import com.envilabindonesia.android.data.response.RequestedMonth
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.asButton
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toIdr
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.util.PrefsUtil
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.item_chart_legend.view.*
import kotlinx.android.synthetic.main.layout_chart.view.*
import kotlinx.android.synthetic.main.layout_supervisor_partner.view.*
import kotlinx.android.synthetic.main.menu_bottom_company.view.*
import kotlinx.android.synthetic.main.menu_bottom_partner.view.*
import javax.inject.Inject


/**
 * Created by galihadityo on 2019-03-11.
 */

class DashboardFragment : BaseFragment(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        requestedMonth?.let {
            viewModel.getDashboard(it.month?.toInt(), it.year?.toInt())
        } ?: viewModel.getDashboard()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<DashboardViewModel>(viewModelFactory) }

    private var requestedMonth: RequestedMonth? = null
    private var dialogMonth: AlertDialog? = null
    private var dashboardResponse: DashboardResponse? = null

    private val mAdapterMyOrder by lazy {
        DashboardOrderAdapter(false) {

        }
    }

    private val mAdapterChildOrder by lazy {
        DashboardOrderAdapter(true) {

        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnSeeAll -> getHomeActivity().fragOrder()
            layoutChild.btnSeeAll -> getHomeActivity().fragOrderSubordinate()
            tvMonth -> showMonthDialog()
            v_menu_bottom_partner.bt_p_create_order -> getHomeActivity().addOrder()
            v_menu_bottom_partner.bt_p_create_company -> getHomeActivity().addClient()
            v_menu_bottom_partner.bt_p_schedule_sampling -> getHomeActivity().fragSchedule()
            v_menu_bottom_partner.bt_p_notification -> getHomeActivity().fragNotification()
            v_menu_bottom_company.bt_c_create_order -> getHomeActivity().addOrder()
            v_menu_bottom_company.bt_c_my_order -> getHomeActivity().fragOrder()
            v_menu_bottom_company.bt_c_service -> getHomeActivity().fragCorporateServiceCategories()
            v_menu_bottom_company.bt_c_notification -> getHomeActivity().fragNotification()
        }
    }

    private fun showMonthDialog() {
        val optionMonths = dashboardResponse?.months?.optionMonths

        val list = optionMonths?.mapTo(ArrayList<String>()) { it.label ?: "" } ?: arrayListOf()
        dialogMonth = getDialogChoice(list) {
            val selectedMonth = optionMonths?.find { it2 -> it2.label == it }
            requestedMonth = RequestedMonth(
                label = selectedMonth?.label,
                month = selectedMonth?.month,
                year = selectedMonth?.year
            )
            onRefresh()
        }

        if (dialogMonth?.isShowing == true) dialogMonth?.dismiss()
        dialogMonth?.show()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_dashboard

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_dashboard)
        initView()
        initListener()
        observer()
        setupRecyclerView()
        setupBottomMenu()
        onRefresh()
        showcase()
    }

    private fun showcase() {
        showcaseViewRect(
            PrefsUtil.SHOWCASE_DASHBOARD_PERFORMA_MONTH,
            R.id.tvMonth,
            getString(R.string.showcase_dashboard_performance_month),
            getString(R.string.showcase_dashboard_performance_month_subtitle)
        ) {}
    }

    override fun onResume() {
        super.onResume()
        checkNotification()
    }

    private fun checkNotification() {
        try {
            if (getHomeActivity().listInbox == null) Handler().postDelayed({checkNotification()}, 250)
            if (getHomeActivity().isNotificationShow() == true) {
                v_menu_bottom_partner.bt_p_notification.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_top_menu_notif_new,
                    0,
                    0
                )
                v_menu_bottom_company.bt_c_notification.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_top_menu_notif_new,
                    0,
                    0
                )
            } else {
                v_menu_bottom_partner.bt_p_notification.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_top_menu_notif,
                    0,
                    0
                )
                v_menu_bottom_company.bt_c_notification.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_top_menu_notif,
                    0,
                    0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupBottomMenu() {
        if (Constant.isLoginAsMitraOrSales()) {
            onClickBottomMenuPartner()
        } else {
            onClickBottomMenuCompany()
        }
    }

    private fun setupRecyclerView() {
        rv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = mAdapterMyOrder
        }

        layoutChild.rvChild.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = mAdapterChildOrder
        }
    }

    private fun initListener() {
        swipeRefresh.setOnRefreshListener(this)
        tvMonth.setOnClickListener(this)
        btnSeeAll.setOnClickListener(this)
        layoutChild.btnSeeAll.setOnClickListener(this)
    }

    private fun initView() {
        tvMonth.asButton()
    }

    private fun observer() {
        viewModel.onSuccess().observe(this, BaseObserver {
            dashboardResponse = it.result
            performance()
            listMyOrders()
            childPerformance()
            listChildOrders()
            pieChart()
        })

        viewModel.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })

        viewModel.onLoading().observe(this, BaseObserver {
            swipeRefresh.isRefreshing = it
        })
    }

    private fun performance() {
        requestedMonth = dashboardResponse?.months?.requestedMonth
        tvMonth.setText(requestedMonth?.label)

        val myPerformance = dashboardResponse?.myPerformance
        val orderSubmit = myPerformance?.submittedOrders
        val orderComplete = myPerformance?.completedOrders
        val orderTotal = myPerformance?.totalOrders
        val orderRevenue = myPerformance?.potentialRevenue

        tvOrderSubmited.text = orderSubmit?.orders.toString()
        layoutOrderSubmit.visibility = if (orderSubmit?.show == true) View.VISIBLE else View.GONE

        tvOrderCompleted.text = orderComplete?.orders.toString()
        layoutOrderComplete.visibility = if (orderComplete?.show == true) View.VISIBLE else View.GONE

        tvOrderTotal.text = orderTotal?.totalAmount?.toIdr() ?: "-"
        layoutOrderTotal.visibility = if (orderTotal?.show == true) View.VISIBLE else View.GONE

        tvOrderRevenue.text = orderRevenue?.totalAmount?.toIdr() ?: "-"
        layoutOrderRevenue.visibility = if (orderRevenue?.show == true) View.VISIBLE else View.GONE
    }

    private fun listMyOrders() {
        dashboardResponse?.myOrders?.preview?.let { mAdapterMyOrder.addAll(ArrayList(it)) }
        layoutListOrder.visibility = if (dashboardResponse?.myOrders?.show == true) View.VISIBLE else View.GONE
    }

    private fun childPerformance() {
        layoutChild.tvChildSubmit.text = dashboardResponse?.myChildOrders?.amount.toString()
        layoutChild.tvChildRevenue.text = dashboardResponse?.myChildOrders?.totalAmount?.toIdr() ?: "-"
    }

    private fun listChildOrders() {
        dashboardResponse?.myChildOrders?.preview?.let { mAdapterChildOrder.addAll(ArrayList(it)) }
        layoutChild.visibility = if (dashboardResponse?.myChildOrders?.show == true) View.VISIBLE else View.GONE
    }

    private fun onClickBottomMenuPartner() {
        vf_menu_bottom.displayedChild = 0
        v_menu_bottom_partner.bt_p_create_order.setOnClickListener(this)
        v_menu_bottom_partner.bt_p_create_company.setOnClickListener(this)
        v_menu_bottom_partner.bt_p_schedule_sampling.setOnClickListener(this)
        v_menu_bottom_partner.bt_p_notification.setOnClickListener(this)
    }

    private fun onClickBottomMenuCompany() {
        vf_menu_bottom.displayedChild = 1
        v_menu_bottom_company.bt_c_create_order.setOnClickListener(this)
        v_menu_bottom_company.bt_c_my_order.setOnClickListener(this)
        v_menu_bottom_company.bt_c_service.setOnClickListener(this)
        v_menu_bottom_company.bt_c_notification.setOnClickListener(this)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        if (Constant.isLoginAsMitraOrSales()) {
//            inflater.inflate(R.menu.dashboard_partner, menu)
//        } else {
//            inflater.inflate(R.menu.dashboard_company, menu)
//        }
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.dashboard_create_client -> {
//                getHomeActivity().addClient()
//                true
//            }
//
//            R.id.dashboard_create_order -> {
//                getHomeActivity().addOrder()
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    @Suppress("DEPRECATION")
    private fun pieChart() {
        val dataChart = dashboardResponse?.myOrdersChart
        includeChart.visibility = if (dataChart?.show == true) View.VISIBLE else View.GONE

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
        dashboardResponse?.myOrdersChart?.chartStats?.forEach {
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