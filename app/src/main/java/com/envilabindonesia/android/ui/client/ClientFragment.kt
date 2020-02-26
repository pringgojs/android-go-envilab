package com.envilabindonesia.android.ui.client

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.adapter.BaseViewHolderLoadmore
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.ui.client.edit.ClientEditActivity
import com.envilabindonesia.android.ui.client.search.SearchClientActivity
import com.envilabindonesia.android.ui.client.search.SearchClientViewModel
import com.envilabindonesia.android.util.PrefsUtil
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-11.
 */

class ClientFragment: BaseFragmentLoadmore<CompanyResponse, ClientAdapter.ViewHolder>() {

    override fun getViewHolder(): BaseViewHolderLoadmore<CompanyResponse, ClientAdapter.ViewHolder> {
        return ClientAdapter()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<SearchClientViewModel>(viewModelFactory) }

    override fun initView() {
        baseActivity?.supportActionBar?.title = resources.getString(R.string.menu_client)

        observe()
        setListener(object : BaseFragmentLoadmore.Listener<CompanyResponse> {
            override fun onPullRefresh() {
                viewModel.getCompanies(mPage)
            }

            override fun onLoadMore(page: Int, totalItem: Int) {
                viewModel.getCompanies(mPage)
            }

            override fun onItemSelected(t: CompanyResponse) {
                activity?.let { ClientEditActivity.start(it, t) }
            }
        })

        showcaseCreate()
    }

    private fun showcaseCreate() {
        showcaseToolbar(
            PrefsUtil.SHOWCASE_CREATE_COMPANY,
            R.id.client_create_client,
            R.drawable.ic_add_toolbar,
            getString(R.string.showcase_company),
            getString(R.string.showcase_company_create)
        ) {}
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
                it.result?.rows?.map { company -> BaseAdapterModel(company.companyName, company) }
                    ?.toCollection(ArrayList())

            if (mPage == 0) addOnRefresh(list) else addOnLoadmore(list)
        })

        viewModel.onError().observe(this, BaseObserver {

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.client, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.client_search -> {
                startActivityForResult(Intent(activity, SearchClientActivity::class.java), Constant.ActivityResult.RC_SEARCH_CLIENT)
                true
            }

            R.id.client_create_client -> {
                getHomeActivity().addClient()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                Constant.ActivityResult.RC_SEARCH_CLIENT -> {
                    activity?.let {
                        ClientEditActivity.start(it,
                            data.getParcelableExtra(SearchClientActivity.BUNDLE)
                                ?: return)
                    }
                }
            }
        }
    }
}