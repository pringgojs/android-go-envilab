package com.envilabindonesia.android.ui.client.contact.list

import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ChangeProfileRequest
import com.envilabindonesia.android.data.request.CompanyAddRequest
import com.envilabindonesia.android.data.request.CompanyEditRequest
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.ContactPersonResponse
import com.envilabindonesia.android.data.response.LoginResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class ContactPersonListViewModel @Inject constructor(private val repository: Repository)
    : BaseViewModel<BasePageResponse<ContactPersonResponse>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun getContactPerson(companyId: Int) {
        showLoading()
        repository.getContactPerson(companyId)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}