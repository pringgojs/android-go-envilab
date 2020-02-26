package com.envilabindonesia.android.ui.faq

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapter
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.response.FAQResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import kotlinx.android.synthetic.main.fragment_faq.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-05.
 */

class FAQFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var mAdapter: BaseAdapter<FAQResponse, FAQAdapter.ViewHolder>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<FAQViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_faq

    override fun init() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_faq)

        swipeRefresh.setOnRefreshListener(this)
        observe()
        onRefresh()

        mAdapter = BaseAdapter(FAQAdapter()) {
            activity?.let { it1 -> it.question?.toast(it1) }
        }

        rv.adapter = mAdapter
    }

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<FAQResponse>> =
                it.result?.map { faq -> BaseAdapterModel(faq.question, faq) }
                    ?.toCollection(ArrayList())
                    ?: arrayListOf()

            mAdapter?.addAll(list)
        })

        viewModel.onLoading().observe(this, BaseObserver {
            swipeRefresh.isRefreshing = it
        })

        viewModel.onError().observe(this, BaseObserver {
            activity?.let { it1 -> it.toast(it1) }
        })
    }

    override fun onRefresh() {
        viewModel.getFAQ()
    }

}