package com.envilabindonesia.android.ui.client.contact

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ContactPersonAddRequest
import com.envilabindonesia.android.data.request.ContactPersonUpdateRequest
import com.envilabindonesia.android.data.response.ContactPersonResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class ContactPersonViewModel @Inject constructor(private val repository: Repository)
    : BaseViewModel<ContactPersonResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postContactPersonAdd(contactPersonAddRequest: ContactPersonAddRequest) {
        showLoading()
        repository.postContactPersonAdd(contactPersonAddRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

    fun postContactPersonUpdate(contactPersonUpdateRequest: ContactPersonUpdateRequest) {
        showLoading()
        repository.postContactPersonUpdate(contactPersonUpdateRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}