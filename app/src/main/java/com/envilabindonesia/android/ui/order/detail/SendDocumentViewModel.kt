package com.envilabindonesia.android.ui.order.detail

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.SendDocumentRequest
import com.envilabindonesia.android.data.response.SendDocumentResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class SendDocumentViewModel @Inject constructor(private val repository: Repository): BaseViewModel<SendDocumentResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postSendDocument(sendDocumentRequest: SendDocumentRequest) {
        showLoading()
        repository.postSendDocument(sendDocumentRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}