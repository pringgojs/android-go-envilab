package com.envilabindonesia.android.ui.services.category

import android.app.Service
import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ChangePasswordRequest
import com.envilabindonesia.android.data.response.ChangePasswordResponse
import com.envilabindonesia.android.data.response.FAQResponse
import com.envilabindonesia.android.data.response.ServiceCategoryResponse
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class CorporateServiceCategoriesViewModel @Inject constructor(private val repository: Repository): BaseViewModel<List<ServiceCategoryResponse>>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun getCategories() {
        showLoading()
        repository.getServiceCategories()
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}