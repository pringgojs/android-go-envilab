package com.envilabindonesia.android.ui.order.search

import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-15.
 */

class OrderSearchFragment: BaseFragmentLoadmore<TransactionResponse, OrderSearchAdapter.ViewHolder>() {

    override fun getViewHolder(): BaseViewHolderLoadmore<TransactionResponse, OrderSearchAdapter.ViewHolder> {
        return OrderSearchAdapter()
    }

    private val parentActivity by lazy {
        (activity as OrderSearchActivity)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<OrderSearchViewModel>(viewModelFactory) }

    override fun initView() {
        observe()
        setListener(object : BaseFragmentLoadmore.Listener<TransactionResponse> {
            override fun onPullRefresh() {
                viewModel.getTransactionByKeyword(mPage, parentActivity.getTextSearch())
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                viewModel.getTransactionByKeyword(mPage, parentActivity.getTextSearch())
            }

            override fun onItemSelected(t: TransactionResponse) {
                parentActivity.sendResult(t)
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
            val list: ArrayList<BaseAdapterModel<TransactionResponse>>? =
                it.result?.rows?.map { company -> BaseAdapterModel(company.companyName, company) }
                    ?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

}