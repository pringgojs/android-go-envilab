package com.envilabindonesia.android.ui.training

import android.content.Intent
import android.net.Uri
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapter
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.data.response.TrainingMaterialResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import kotlinx.android.synthetic.main.fragment_faq.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-05.
 */

class TrainingPdfFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var mAdapter: BaseAdapter<TrainingMaterialResponse, TrainingPdfAdapter.ViewHolder>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<TrainingMaterialViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_training_pdf

    override fun init() {
        (parentFragment?.parentFragment as TrainingMaterialFragment).setCheckedChip(getString(R.string.training_pdf))

        swipeRefresh.setOnRefreshListener(this)
        observe()
        onRefresh()

        mAdapter = BaseAdapter(TrainingPdfAdapter()) {
            try {
                startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse(it.file), "application/pdf")
                        flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    }
                )
            } catch (e: Exception) {
                activity?.let { it1 -> e.message?.toast(it1) }
            }
        }

        rv.adapter = mAdapter
    }

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<TrainingMaterialResponse>> = it.result?.map { data ->
                BaseAdapterModel(data.title, data, R.drawable.ic_pdf)
            }?.toCollection(ArrayList()) ?: arrayListOf()

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
        viewModel.getTrainingMaterial("PDF")
    }

}