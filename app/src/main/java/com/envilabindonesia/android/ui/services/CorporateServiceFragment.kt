package com.envilabindonesia.android.ui.services

import android.os.Bundle
import android.util.Log
import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.response.ServiceCategoryResponse
import com.envilabindonesia.android.data.response.ServiceCorporateResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-20.
 */

class CorporateServiceFragment
    : BaseFragmentLoadmore<ServiceCorporateResponse, CorporateServiceAdapter.ViewHolder>() {

    companion object {
        const val BUNDLE = "BUNDLE"

        fun newInstance(serviceCorporateResponse: ServiceCategoryResponse): CorporateServiceFragment {
            val fragment = CorporateServiceFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE, serviceCorporateResponse)
            }
            return fragment
        }

    }

    override fun getViewHolder()
            : BaseViewHolderLoadmore<ServiceCorporateResponse, CorporateServiceAdapter.ViewHolder> {
        return CorporateServiceAdapter()
    }

    private var serviceCategoryResponse: ServiceCategoryResponse? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<CorporateServiceViewModel>(viewModelFactory) }

    override fun initView() {
        serviceCategoryResponse = arguments?.getParcelable(BUNDLE)

        observe()
        setListener(object : BaseFragmentLoadmore.Listener<ServiceCorporateResponse> {
            override fun onPullRefresh() {
                serviceCategoryResponse?.catId?.let { viewModel.getCorporateServiceById(mPage, it) }
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                serviceCategoryResponse?.catId?.let { viewModel.getCorporateServiceById(mPage, it) }
            }

            override fun onItemSelected(t: ServiceCorporateResponse) {

            }
        })

        onRefresh()
    }

    private fun observe() {
        viewModel.onLoading().observe(this, BaseObserver {
            showLoading(it)
        })

        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<ServiceCorporateResponse>>? =
                it.result?.rows?.map { data -> BaseAdapterModel(data.catName, data) }
                    ?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

}