package com.envilabindonesia.android.ui.order.list.item

import android.os.Bundle
import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.data.response.TransactionStatusResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.ui.order.detail.OrderDetailActivity
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-20.
 */

class OrderListPerItemFragment
    : BaseFragmentLoadmore<TransactionResponse, OrderListPerItemAdapter.ViewHolder>() {

    companion object {
        const val BUNDLE = "BUNDLE"
        const val BUNDLE_SUBORDINATE = "BUNDLE_SUBORDINATE"

        fun newInstance(transactionStatusResponse: TransactionStatusResponse, isSubordinate: Boolean): OrderListPerItemFragment {
            val fragment = OrderListPerItemFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE, transactionStatusResponse)
                putBoolean(BUNDLE_SUBORDINATE, isSubordinate)
            }
            return fragment
        }
    }

    override fun getViewHolder()
            : BaseViewHolderLoadmore<TransactionResponse, OrderListPerItemAdapter.ViewHolder> {
        return OrderListPerItemAdapter()
    }

    private var transactionStatusResponse: TransactionStatusResponse? = null
    private var isSubordinate: Boolean? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<OrderListPerItemViewModel>(viewModelFactory) }

    override fun initView() {
        transactionStatusResponse = arguments?.getParcelable(BUNDLE)
        isSubordinate = arguments?.getBoolean(BUNDLE_SUBORDINATE)

        observe()
        setListener(object : BaseFragmentLoadmore.Listener<TransactionResponse> {
            override fun onPullRefresh() {
                transactionStatusResponse?.statusId?.let { viewModel.getTransactionByStatusId(mPage, it, isSubordinate ?: false) }
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                transactionStatusResponse?.statusId?.let { viewModel.getTransactionByStatusId(mPage, it, isSubordinate ?: false) }
            }

            override fun onItemSelected(t: TransactionResponse) {
                activity?.let {
                    OrderDetailActivity.start(it, t)
                }
            }
        })

        onRefresh()
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver {
            showLoading(it)
        })

        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<TransactionResponse>>? =
                it.result?.rows?.map { data -> BaseAdapterModel(data.companyName, data) }
                    ?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

}