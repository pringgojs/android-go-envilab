package com.envilabindonesia.android.ui.account.password

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ChangePasswordRequest
import com.envilabindonesia.android.data.response.ChangePasswordResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class PasswordViewModel @Inject constructor(private val repository: Repository): BaseViewModel<ChangePasswordResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postChangePassword(changePasswordRequest: ChangePasswordRequest) {
        showLoading()
        repository.postChangePassword(changePasswordRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}