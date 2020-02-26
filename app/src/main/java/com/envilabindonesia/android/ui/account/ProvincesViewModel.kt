package com.envilabindonesia.android.ui.account

import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.response.ProvincesResponse
import com.envilabindonesia.android.data.response.UploadResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class ProvincesViewModel @Inject constructor(private val repository: Repository): BaseViewModel<BasePageResponse<ProvincesResponse>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun getProvinces() {
        showLoading()
        repository.getProvinces()
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}