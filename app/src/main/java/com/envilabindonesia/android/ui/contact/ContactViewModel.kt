package com.envilabindonesia.android.ui.contact

import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.ContactSupportResponse
import com.envilabindonesia.android.data.response.MyInboxResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class ContactViewModel @Inject constructor(private val repository: Repository)
    : BaseViewModel<List<ContactSupportResponse>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun getContactSupport() {
        showLoading()
        repository.getContactSupport()
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}