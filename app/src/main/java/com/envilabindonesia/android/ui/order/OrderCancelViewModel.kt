package com.envilabindonesia.android.ui.order

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.OrderCancelRequest
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-05-25.
 */

class OrderCancelViewModel @Inject constructor(private val repository: Repository): BaseViewModel<Any>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postOrderCancel(orderCancelRequest: OrderCancelRequest) {
        showLoading()
        repository.postOrderCancel(orderCancelRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}