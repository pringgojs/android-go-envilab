package com.envilabindonesia.android.ui.home

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.envilabindonesia.android.BuildConfig
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseResponse
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.response.LoginResponse
import com.envilabindonesia.android.data.response.MyInboxResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.RxBus
import com.envilabindonesia.android.extension.RxEvent
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.loadCircle
import com.envilabindonesia.android.ui.client.add.ClientAddActivity
import com.envilabindonesia.android.ui.notification.NotificationViewModel
import com.envilabindonesia.android.ui.order.add.MemberStatusViewModel
import com.envilabindonesia.android.ui.order.add.OrderAddActivity
import com.envilabindonesia.android.ui.webview.WebviewFragment
import com.envilabindonesia.android.ui.webview.WebviewViewModel
import com.envilabindonesia.android.util.PrefsUtil
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.nav_header_dashboard.view.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<HomeViewModel>(viewModelFactory) }
    private val viewModelMember by lazy { getViewModel<MemberStatusViewModel>(viewModelFactory) }
    private val viewModelNotification by lazy { getViewModel<NotificationViewModel>(viewModelFactory) }
    private val viewModelWebView by lazy { getViewModel<WebviewViewModel>(viewModelFactory) }

    var listInbox: List<MyInboxResponse>? = null
    private var headerView: View? = null
    private var dialogAppVersionDialog: AppVersionDialog? = null
    private var menuId: Int? = null

    override fun getLayoutRes(): Int = R.layout.activity_dashboard

    private val navController by lazy {
        Navigation.findNavController(this, R.id.navHostDashboard)
    }

    override fun init() {
        setSupportActionBar(toolbar)
        setNavigationDrawer()
        setNavigationDrawerHeader()
        observerNotification()
        observerMember()
        observerWebView()
        notificationCount(false) // set default view to hide new notification
        if (Constant.isLoginAsUnverified()) fragCorporateServiceCategories()
        checkAppVersion()
    }

    private fun observerWebView() {
        viewModelWebView.onSuccess().observe(this, BaseObserver {
            val youtubeId = it.result?.content?.substringAfter('>')?.substringBefore('<')
            PrefsUtil.write(PrefsUtil.YOUTUBE_ID, youtubeId)
        })
    }

    private fun observerMember() {
        viewModelMember.onSuccess().observe(this, BaseObserver {
            if (Constant.isLoginAsUnverified()) {
                if (it.result?.isVerified == true) {
                    RxBus.post(RxEvent.Logout(it.message))
                } else {
                    Handler().postDelayed({
                        if (!isFinishing) viewModelMember.getMemberStatus()
                    }, 5000)
                }
            }
        })
    }

    private fun observerNotification() {
        viewModelNotification.onSuccess().observe(this, BaseObserver {
            listInbox = it.result?.rows
            if (it.result == null) {
                notificationCount(false)
            } else {
                if (it.result.startIndex == 0) setNotificationCount(it)
            }
        })
    }

    private fun checkAppVersion() {
        val playstoreVersion = PrefsUtil.appVersion()
        val localVersion = BuildConfig.VERSION_CODE
        if (playstoreVersion > localVersion) {
            if (dialogAppVersionDialog == null) {
                dialogAppVersionDialog = AppVersionDialog()
                dialogAppVersionDialog?.setOnListener(object : AppVersionDialog.OnListener {
                    override fun onUpdate() {
                        val rateIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + applicationContext.packageName))
                        var marketFound = false
                        val otherApps = applicationContext.packageManager.queryIntentActivities(rateIntent, 0)
                        for (otherApp in otherApps) {

                            if (otherApp.activityInfo.applicationInfo.packageName == "com.android.vending") {
                                val otherAppActivity = otherApp.activityInfo
                                val componentName = ComponentName(
                                    otherAppActivity.applicationInfo.packageName,
                                    otherAppActivity.name
                                )
                                rateIntent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                                        Intent.FLAG_ACTIVITY_NEW_TASK
                                rateIntent.component = componentName
                                startActivity(rateIntent)
                                marketFound = true
                                break
                            }
                        }

                        if (!marketFound) {
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                            )
                            webIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(webIntent)
                        }
                    }
                })
            }

            try {
                if (dialogAppVersionDialog?.isVisible == true) dialogAppVersionDialog?.dismissAllowingStateLoss()
                dialogAppVersionDialog?.show(supportFragmentManager, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setNotificationCount(it: BaseResponse<BasePageResponse<MyInboxResponse>>?) {
        if (it?.result?.rows?.size == 0) {
            notificationCount(false)
            return
        }

        val isAnyInbox = it?.result?.rows?.find { myInboxResponse -> myInboxResponse.isRead == false }
        notificationCount(isAnyInbox != null)
    }

    override fun onResume() {
        super.onResume()
        viewModelMember.getMemberStatus()
        viewModel.getMasterData()
        viewModelWebView.getStaticPages("youtubeid")
        getFcmToken()
        viewModelNotification.getMyInbox(0)
    }

    private fun getFcmToken() {
        try {
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                it.result?.let { result ->
                    PrefsUtil.setFcmToken(result.token)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!PrefsUtil.getFcmToken().isEmpty()) {
            viewModel.postRegisterDevice(PrefsUtil.getFcmToken())
        }
    }

    fun notificationCount(isShow: Boolean) {
        nav_view.menu.findItem(R.id.nav_notification).actionView.visibility =
                if (isShow) View.VISIBLE else View.INVISIBLE
        nav_view.menu.findItem(R.id.nav_notification).actionView.invalidate()
    }

    fun isNotificationShow(): Boolean? = nav_view.menu.findItem(R.id.nav_notification).actionView.isVisible

    private fun setNavigationDrawerHeader() {
        headerView = nav_view.getHeaderView(0)
        headerView?.let {
            val loginResponse = PrefsUtil.read(PrefsUtil.LOGIN, LoginResponse::class.java)
            val imageUrl = loginResponse?.profileImage ?: ""
            it.navTvName.text = loginResponse?.fullName ?: ""
            it.navTvPhone.text = loginResponse?.phone ?: ""
            it.navTvStatus.text = loginResponse?.registeredAsLabel ?: ""

            if (Constant.isLoginAsUnverified()) {
                it.navTvStatus.setBackgroundResource(R.drawable.tv_orange_rounded)
            } else {
                it.navTvStatus.setBackgroundResource(R.drawable.tv_green_rounded)
            }

            if (imageUrl.isEmpty())
                it.imageView.loadCircle(R.mipmap.ic_launcher_round)
            else
                it.imageView.loadCircle(imageUrl)
        }
    }

    private fun setNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.menu.clear()
        nav_view.inflateMenu(
            when {
                Constant.isLoginAsUnverified() -> {
                    menuId = R.menu.menu_guest
                    R.menu.menu_guest
                }
                Constant.isLoginAsMitra() -> R.menu.menu_partner
                Constant.isLoginAsSales() -> R.menu.menu_sales
                else -> R.menu.menu_company
            }
        )

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(if (Constant.isLoginAsUnverified()) R.id.nav_corporate else R.id.nav_dashboard)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (Constant.isLoginAsUnverified() || menuId != null && menuId == R.menu.menu_guest) {
                if (navController.currentDestination?.label?.equals(getString(R.string.navigation_corporate_service)) == true) {
                    super.onBackPressed()
                } else {
                    fragCorporateServiceCategories()
                }
            } else {
                if (navController.currentDestination?.label?.equals(getString(R.string.navigation_dashboard)) == true) {
                    super.onBackPressed()
                } else {
                    fragDashboard()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_dashboard -> fragDashboard()
            R.id.nav_order -> fragOrder()
            R.id.nav_order_subordinate -> fragOrderSubordinate()
            R.id.nav_client -> fragClient()
            R.id.nav_performance -> fragPerformance()
            R.id.nav_support -> fragContact()
            R.id.nav_account -> fragAccount()
            R.id.nav_faq -> fragFAQ()
            R.id.nav_tnc -> fragWebView(resources.getString(R.string.menu_tnc), "terms-and-conditions")
            R.id.nav_partner -> fragWebView(resources.getString(R.string.menu_partnership), "partnership-agreement")
            R.id.nav_training -> fragTraining()
            R.id.nav_corporate -> fragCorporateServiceCategories()
            R.id.nav_notification -> fragNotification()
            R.id.nav_schedule -> fragSchedule()
            R.id.nav_logout -> logout()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun logout() {
        RxBus.post(RxEvent.Logout())
    }

    fun fragSchedule() {
        nav_view.setCheckedItem(R.id.nav_schedule)
        navController.navigate(R.id.scheduleFragment)
    }

    fun fragNotification() {
        nav_view.setCheckedItem(R.id.nav_notification)
        navController.navigate(R.id.notificationFragment)
    }

    fun fragCorporateServiceCategories() {
        nav_view.setCheckedItem(R.id.nav_corporate)
        navController.navigate(R.id.corporateServiceCategoriesFragment)
    }

    private fun fragTraining() {
        nav_view.setCheckedItem(R.id.nav_training)
        navController.navigate(R.id.trainingMaterialFragment)
    }

    private fun fragWebView(title: String, slug: String) {
        nav_view.setCheckedItem(R.id.nav_tnc)
        navController.navigate(
            R.id.webviewFragment, bundleOf(
                Pair(WebviewFragment.BUNDLE_TITLE, title),
                Pair(WebviewFragment.BUNDLE_SLUG, slug)
            )
        )
    }

    private fun fragFAQ() {
        nav_view.setCheckedItem(R.id.nav_faq)
        navController.navigate(R.id.faqFragment)
    }

    private fun fragContact() {
        nav_view.setCheckedItem(R.id.nav_support)
        navController.navigate(R.id.contactFragment)
    }

    private fun fragDashboard() {
        nav_view.setCheckedItem(R.id.nav_dashboard)
        navController.navigate(R.id.dashboardFragment)
    }

    fun fragAccount() {
        nav_view.setCheckedItem(R.id.nav_account)
        navController.navigate(R.id.accountFragment)
    }

    fun fragOrder() {
        PrefsUtil.write(PrefsUtil.ORDER_IS_SUBORDINATE, false)
        nav_view.setCheckedItem(R.id.nav_order)
        navController.navigate(R.id.orderFragment)
    }

    fun fragOrderSubordinate() {
        PrefsUtil.write(PrefsUtil.ORDER_IS_SUBORDINATE, true)
        nav_view.setCheckedItem(R.id.nav_order_subordinate)
        navController.navigate(R.id.orderFragment)
    }

    fun fragClient() {
        nav_view.setCheckedItem(R.id.nav_client)
        navController.navigate(R.id.clientFragment)
    }

    fun fragPerformance() {
        nav_view.setCheckedItem(R.id.nav_performance)
        navController.navigate(R.id.performanceFragment)
    }

    fun addClient() {
        startActivity(Intent(applicationContext, ClientAddActivity::class.java))
    }

    fun addOrder() {
        startActivity(Intent(applicationContext, OrderAddActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        dialogAppVersionDialog?.let {
            it.dismissAllowingStateLoss()
            dialogAppVersionDialog = null
        }
    }

}