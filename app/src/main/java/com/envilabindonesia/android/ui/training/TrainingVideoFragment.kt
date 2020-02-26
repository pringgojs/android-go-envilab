package com.envilabindonesia.android.ui.training

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseFragment
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.data.response.TrainingMaterialResponse
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.extension.toast
import com.envilabindonesia.android.util.DeviceUtil
import com.envilabindonesia.android.util.PrefsUtil
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.fragment_faq.*
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-04-05.
 */

class TrainingVideoFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private var mAdapter: TrainingVideoAdapter<TrainingMaterialResponse>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<TrainingMaterialViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.fragment_training_video

    override fun init() {
        (parentFragment?.parentFragment as TrainingMaterialFragment).setCheckedChip(getString(R.string.training_video))

        swipeRefresh.setOnRefreshListener(this)
        observe()
        onRefresh()

        val height = DeviceUtil.getWidth() / 2
        mAdapter = TrainingVideoAdapter(height) {
            val idVideo = it.file?.substringAfter("=") ?: ""
            if (idVideo.isEmpty()) {
                activity?.let { it1 -> getString(R.string.failed_get_video).toast(it1) }
                return@TrainingVideoAdapter
            }

            PrefsUtil.read(PrefsUtil.YOUTUBE_ID, null)?.let { youtubeId ->
                startActivity(YouTubeStandalonePlayer.createVideoIntent(activity, youtubeId, idVideo))
            }
        }

        rv.adapter = mAdapter
    }

    private fun observe() {
        viewModel.onSuccess().observe(this, BaseObserver {
            val list: ArrayList<BaseAdapterModel<TrainingMaterialResponse>> = it.result?.map {
                    data -> BaseAdapterModel(data.title, data) }?.toCollection(ArrayList()) ?: arrayListOf()

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
        viewModel.getTrainingMaterial("VIDEO")
    }

}