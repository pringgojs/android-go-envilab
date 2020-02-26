package com.envilabindonesia.android.ui.register

import androidx.lifecycle.MutableLiveData
import com.envilabindonesia.android.base.BaseResponse
import com.envilabindonesia.android.base.BaseConsumer
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.RegisterRequest
import com.envilabindonesia.android.data.response.RegisterResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class RegisterViewModel @Inject constructor(private val repository: Repository): BaseViewModel<RegisterResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun doRegister(registerRequest: RegisterRequest) {
        showLoading()
        repository.postRegister(registerRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}