package com.envilabindonesia.android.ui.client.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.ui.client.edit.ClientEditActivity
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-15.
 */

class SearchClientFragment: BaseFragmentLoadmore<CompanyResponse, SearchClientAdapter.ViewHolder>() {

    override fun getViewHolder(): BaseViewHolderLoadmore<CompanyResponse, SearchClientAdapter.ViewHolder> {
        return SearchClientAdapter()
    }

    private val parentActivity by lazy {
        (activity as SearchClientActivity)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<SearchClientViewModel>(viewModelFactory) }

    override fun initView() {
        observe()
        setListener(object : BaseFragmentLoadmore.Listener<CompanyResponse> {
            override fun onPullRefresh() {
                viewModel.getCompanies(mPage, parentActivity.getTextSearch())
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                viewModel.getCompanies(mPage, parentActivity.getTextSearch())
            }

            override fun onItemSelected(t: CompanyResponse) {
//                activity?.setResult(RESULT_OK, Intent().apply {
//                    putExtra(SearchClientActivity.BUNDLE, t)
//                })
//                activity?.finish()
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
            val list: ArrayList<BaseAdapterModel<CompanyResponse>>? =
                it.result?.rows?.map {
                        company ->
                    BaseAdapterModel(company.companyName, company)
                }
                    ?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

}