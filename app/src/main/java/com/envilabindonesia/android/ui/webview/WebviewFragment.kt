package com.envilabindonesia.android.ui.webview

import android.webkit.WebView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import kotlinx.android.synthetic.main.fragment_webview.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-05.
 */

class WebviewFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val BUNDLE_TITLE = "BUNDLE_TITLE"
        const val BUNDLE_SLUG = "BUNDLE_SLUG"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<WebviewViewModel>(viewModelFactory) }

    private val webview by lazy {
        WebView(activity)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_webview

    override fun init() {
        baseActivity?.supportActionBar?.title = arguments?.getString(BUNDLE_TITLE)
        wvPlaceholder.addView(webview)
        swipeRefresh.setOnRefreshListener(this)
        observe()
    }

    override fun onDetach() {
        super.onDetach()
        if (wvPlaceholder != null) wvPlaceholder.removeAllViews()
        webview.tag = null
        webview.clearHistory()
        webview.removeAllViews()
        webview.destroy()
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            webview.loadData(it.result?.content, "text/html; charset=utf-8", "UTF-8")
        })

        viewModel.onLoading().observe(this, BaseObserver {
            swipeRefresh.isRefreshing = it
        })

        viewModel.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })
    }

    override fun onRefresh() {
        viewModel.getStaticPages(arguments?.getString(BUNDLE_SLUG) ?: return)
    }

}