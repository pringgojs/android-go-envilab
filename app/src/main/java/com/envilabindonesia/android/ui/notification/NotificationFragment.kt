package com.envilabindonesia.android.ui.notification

import android.os.Handler
import androidx.lifecycle.Observer
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.MyInboxResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.home.HomeActivity
import com.envilabindonesia.android.ui.notification.detail.NotificationDetailActivity
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-28.
 */

class NotificationFragment: BaseFragmentLoadmore<MyInboxResponse, NotificationAdapter.ViewHolder>() {

    override fun getViewHolder(): BaseViewHolderLoadmore<MyInboxResponse, NotificationAdapter.ViewHolder> {
        return NotificationAdapter()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<NotificationViewModel>(viewModelFactory) }

    override fun initView() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_notif)

        observe()
        setListener(object : BaseFragmentLoadmore.Listener<MyInboxResponse> {
            override fun onPullRefresh() {
                viewModel.getMyInbox(mPage)
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                viewModel.getMyInbox(mPage)
            }

            override fun onItemSelected(t: MyInboxResponse) {
                activity?.let { NotificationDetailActivity.start(it, t) }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver {
            showLoading(it)
        })

        viewModel.onSuccess().observe(this, BaseObserver {
            if (it.result == null) {
                (baseActivity as HomeActivity).notificationCount(false)
                activity?.let { it1 ->
                    if (mPage == 0) it.message?.toast(it1)
                }
            } else {
                if (it.result.startIndex == 0) {
                    (baseActivity as HomeActivity).setNotificationCount(it)
                }
            }

            val list: ArrayList<BaseAdapterModel<MyInboxResponse>>? =
                it.result?.rows?.map { datas -> BaseAdapterModel(datas.message, datas) }
                    ?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

}