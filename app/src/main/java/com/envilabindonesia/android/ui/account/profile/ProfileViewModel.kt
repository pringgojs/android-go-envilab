package com.envilabindonesia.android.ui.account.profile

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ChangeProfileRequest
import com.envilabindonesia.android.data.response.LoginResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class ProfileViewModel @Inject constructor(private val repository: Repository): BaseViewModel<LoginResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postChangeProfile(changeProfileRequest: ChangeProfileRequest) {
        showLoading()
        repository.postChangeProfile(changeProfileRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}