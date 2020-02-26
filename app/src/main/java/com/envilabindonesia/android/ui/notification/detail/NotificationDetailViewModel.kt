package com.envilabindonesia.android.ui.notification.detail

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ReadOrDeleteInboxRequest
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBack
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class NotificationDetailViewModel @Inject constructor(private val repository: Repository)
    : BaseViewModel<Any>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postInboxRead(id: Int) {
        repository.postInboxRead(ReadOrDeleteInboxRequest(id))
            .performOnBack()
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun postInboxDelete(id: Int) {
        showLoading()
        repository.postInboxDelete(ReadOrDeleteInboxRequest(id))
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}