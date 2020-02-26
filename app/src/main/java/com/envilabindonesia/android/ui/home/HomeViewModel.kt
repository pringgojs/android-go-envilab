package com.envilabindonesia.android.ui.home

import com.envilabindonesia.android.base.BaseResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.RegisterDeviceRequest
import com.envilabindonesia.android.data.response.MemberStatusResponse
import com.envilabindonesia.android.data.response.TransactionStatusResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBack
import com.envilabindonesia.android.util.PrefsUtil
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class HomeViewModel @Inject constructor(private val repository: Repository): BaseViewModel<MemberStatusResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postRegisterDevice(token: String) {
        repository.postRegisterDevice(RegisterDeviceRequest(token))
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

    fun getMasterData() {
        Observable.zip(
            repository.getTransactionStatusObservable(),
            repository.getTransactionStatusSplash(),
            BiFunction<Response<BaseResponse<List<TransactionStatusResponse>>>, Response<BaseResponse<List<TransactionStatusResponse>>>, Any> {
                    _, statusTransactionSplash ->

                if (statusTransactionSplash.isSuccessful && statusTransactionSplash.body() != null && statusTransactionSplash.body()?.result != null ) {
                    PrefsUtil.setTransactionStatus(statusTransactionSplash.body()?.result)
                }

                return@BiFunction Any()
            })
            .performOnBack()
            .subscribe ({

            }, {

            })
            .addTo(compositeDisposable)
    }

    fun getMasterDataSplash() {
        Observable.zip(
            repository.getTransactionStatusSplash(),
            repository.getMemberStatus(),
            BiFunction<Response<BaseResponse<List<TransactionStatusResponse>>>, Response<BaseResponse<MemberStatusResponse>>, Any> {
                    statusTransaction, _ ->
                if (statusTransaction.isSuccessful && statusTransaction.body() != null && statusTransaction.body()?.result != null ) {
                    PrefsUtil.setTransactionStatus(statusTransaction.body()?.result)
                }

                return@BiFunction Any()
            })
            .performOnBack()
            .subscribe ({

            }, {

            })
            .addTo(compositeDisposable)
    }

}