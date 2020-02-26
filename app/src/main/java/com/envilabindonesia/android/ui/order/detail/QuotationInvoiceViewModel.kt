package com.envilabindonesia.android.ui.order.detail

import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-11-04.
 */

class QuotationInvoiceViewModel @Inject constructor(private val repository: Repository)
    : BaseViewModel<BasePageResponse<TransactionResponse>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun getTransactionByOrderId(orderId: Int) {
        showLoading()
        repository.getTransactionByOrderId(orderId)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}