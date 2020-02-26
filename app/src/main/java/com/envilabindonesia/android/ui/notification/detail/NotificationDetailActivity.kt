package com.envilabindonesia.android.ui.notification.detail

import android.content.Context
import android.content.Intent
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.response.MyInboxResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import kotlinx.android.synthetic.main.activity_notification_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-28.
 */

class NotificationDetailActivity: BaseActivity() {

    companion object {
        val BUNDLE = "BUNDLE"
        fun start(context: Context, myInboxResponse: MyInboxResponse) {
            context.startActivity(Intent(context, NotificationDetailActivity::class.java).apply {
                putExtra(BUNDLE, myInboxResponse)
            })
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<NotificationDetailViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_notification_detail

    override fun init() {
        setSupportActionBar(include_toolbar.toolbar)
        setTitleBar(resources.getString(R.string.menu_notif))
        setBackButton(include_toolbar.toolbar) {
            finish()
        }

        val myInboxResponse = intent.getParcelableExtra<MyInboxResponse>(BUNDLE)
        tvSubject.text = myInboxResponse.subject
        webview.loadData(myInboxResponse.message, "text/html; charset=utf-8", "UTF-8")

        observe()

        setLoadingButton(R.id.includeButton, R.string.btn_delete) {
            viewModel.postInboxDelete(myInboxResponse.inboxId ?: return@setLoadingButton)
        }

        viewModel.postInboxRead(myInboxResponse.inboxId ?: return)
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver{
            isLoading(it)
        })

        viewModel.onError().observe(this, BaseObserver{
            it.toast(this)
        })

        viewModel.onSuccess().observe(this, BaseObserver{
            it.message?.toast(this)
            finish()
        })
    }

}
