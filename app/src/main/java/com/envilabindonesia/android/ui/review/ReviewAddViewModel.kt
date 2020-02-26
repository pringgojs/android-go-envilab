package com.envilabindonesia.android.ui.review

import com.envilabindonesia.android.base.BaseViewModel
import com.envilabindonesia.android.data.repository.Repository
import com.envilabindonesia.android.data.request.ReviewRequest
import com.envilabindonesia.android.extension.addTo
import com.envilabindonesia.android.extension.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class ReviewAddViewModel @Inject constructor(private val repository: Repository)
    : BaseViewModel<Any>() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun getDisposable(): CompositeDisposable? = compositeDisposable

    fun postReview(request: ReviewRequest) {
        showLoading()
        repository.postReview(request)
            .performOnBackOutOnMain()
            .subscribe(getBaseConsumer())
            .addTo(compositeDisposable)
    }

}