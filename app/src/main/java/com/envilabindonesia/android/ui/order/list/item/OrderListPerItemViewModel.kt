package com.envilabindonesia.android.ui.order.list.item

import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.response.TransactionResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class OrderListPerItemViewModel @Inject constructor(private val repository: Repository)
    : BaseViewModel<BasePageResponse<TransactionResponse>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun getTransactionByStatusId(page: Int, status: Int, isSubordinate: Boolean) {
        showLoading()
        repository.getTransactionByStatusId(page, status, isSubordinate)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}