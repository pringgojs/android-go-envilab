package com.envilabindonesia.android.ui.services.search

import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.ServiceCorporateResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-15.
 */

class SearchServiceFragment: BaseFragmentLoadmore<ServiceCorporateResponse, SearchServiceAdapter.ViewHolder>() {

    override fun getViewHolder(): BaseViewHolderLoadmore<ServiceCorporateResponse, SearchServiceAdapter.ViewHolder> {
        return SearchServiceAdapter()
    }

    private val parentActivity by lazy {
        (activity as SearchServiceActivity)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<SearchServiceViewModel>(viewModelFactory) }

    override fun initView() {
        observe()
        setListener(object : BaseFragmentLoadmore.Listener<ServiceCorporateResponse> {
            override fun onPullRefresh() {
                viewModel.getServiceByKeyword(mPage, parentActivity.getTextSearch())
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                viewModel.getServiceByKeyword(mPage, parentActivity.getTextSearch())
            }

            override fun onItemSelected(t: ServiceCorporateResponse) {
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
            val list: ArrayList<BaseAdapterModel<ServiceCorporateResponse>>? =
                it.result?.rows?.map { data -> BaseAdapterModel(data.catName, data) }?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

}