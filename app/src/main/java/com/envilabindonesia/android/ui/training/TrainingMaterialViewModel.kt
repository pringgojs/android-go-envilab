package com.envilabindonesia.android.ui.training

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ChangePasswordRequest
import com.envilabindonesia.android.data.response.ChangePasswordResponse
import com.envilabindonesia.android.data.response.FAQResponse
import com.envilabindonesia.android.data.response.TrainingMaterialResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class TrainingMaterialViewModel @Inject constructor(private val repository: Repository): BaseViewModel<List<TrainingMaterialResponse>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun getTrainingMaterial(type: String) {
        showLoading()
        repository.getTrainingMaterial(type)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}