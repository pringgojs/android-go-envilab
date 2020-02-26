package com.envilabindonesia.android.ui.login

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.LoginRequest
import com.envilabindonesia.android.data.response.LoginResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class LoginViewModel @Inject constructor(private val repository: Repository): BaseViewModel<LoginResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postLogin(loginRequest: LoginRequest) {
        showLoading()
        repository.postLogin(loginRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}