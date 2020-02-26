package com.envilabindonesia.android.ui.account.upload

import com.envilabindonesia.android.base.BaseConsumer
import com.envilabindonesia.android.base.BaseResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ChangePasswordRequest
import com.envilabindonesia.android.data.request.ItemAttachment
import com.envilabindonesia.android.data.response.ChangePasswordResponse
import com.envilabindonesia.android.data.response.FAQResponse
import com.envilabindonesia.android.data.response.UploadResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import com.envilabindonesia.android.util.MultipartHelper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class UploadViewModel @Inject constructor(private val repository: Repository): BaseViewModel<UploadResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postUpload(multipartBody: MultipartBody.Part) {
        showLoading()
        repository.postUpload(multipartBody)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}