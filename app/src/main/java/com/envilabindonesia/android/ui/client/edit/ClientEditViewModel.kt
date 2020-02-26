package com.envilabindonesia.android.ui.client.edit

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ChangeProfileRequest
import com.envilabindonesia.android.data.request.CompanyAddRequest
import com.envilabindonesia.android.data.request.CompanyEditRequest
import com.envilabindonesia.android.data.response.CompanyResponse
import com.envilabindonesia.android.data.response.LoginResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class ClientEditViewModel @Inject constructor(private val repository: Repository): BaseViewModel<CompanyResponse>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postCompanyEdit(companyEditRequest: CompanyEditRequest) {
        showLoading()
        repository.postCompanyEdit(companyEditRequest)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}