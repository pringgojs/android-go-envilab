package com.envilabindonesia.android.ui.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.envilabindonesia.android.base.BaseConsumer
import com.envilabindonesia.android.base.BaseResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.response.ValidateCodeResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class VerificationViewModel @Inject constructor(private val repository: Repository): BaseViewModel<ValidateCodeResponse>() {

    private val responseResend: MutableLiveData<BaseResponse<Any>> = MutableLiveData()
    private val isLoadingResend: MutableLiveData<Boolean> = MutableLiveData()
    private val errorMessageResend: MutableLiveData<String> = MutableLiveData()

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun onSuccessResend(): LiveData<BaseResponse<Any>> = responseResend

    fun onLoadingResend(): LiveData<Boolean> = isLoadingResend

    fun onErrorResend(): LiveData<String> = errorMessageResend

    fun getValidateCode(email: String, code: String) {
        showLoading()
        repository.getValidateCode(email, code)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

    fun getResendCode(email: String) {
        isLoadingResend.value = true
        repository.getResendCode(email)
            .performOnBackOutOnMain()
            .subscribe(object : BaseConsumer<Any>() {
                override fun isLoadingObserver(): MutableLiveData<Boolean> = isLoadingResend

                override fun responseObserver(): MutableLiveData<BaseResponse<Any>> = responseResend

                override fun errorObserver(): MutableLiveData<String> = errorMessageResend

            })
            .addTo(compositeDisposable)
    }

}