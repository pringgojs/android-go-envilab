package com.envilabindonesia.android.ui.search

import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-15.
 */

class SearchFragment: BaseFragmentLoadmore<CompanyResponse, CompanyAdapter.ViewHolder>() {

    override fun getViewHolder(): BaseViewHolderLoadmore<CompanyResponse, CompanyAdapter.ViewHolder> {
        return CompanyAdapter()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<SearchViewModel>(viewModelFactory) }

    override fun initView() {
        observe()
        setListener(object : BaseFragmentLoadmore.Listener<CompanyResponse> {
            override fun onPullRefresh() {
                viewModel.getCompanies(mPage)
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                viewModel.getCompanies(mPage)
            }

            override fun onItemSelected(t: CompanyResponse) {

            }
        })
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver {
            showLoading(it)
        })

        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<CompanyResponse>>? =
                it.result?.rows?.map { company -> BaseAdapterModel(company.companyName, company) }
                    ?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

}