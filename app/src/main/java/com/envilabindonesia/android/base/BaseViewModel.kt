package com.envilabindonesia.android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by galihadityo on 2019-02-26.
 */

abstract class BaseViewModel<T>: ViewModel() {

    val response: MutableLiveData<BaseResponse<T>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun onSuccess(): LiveData<BaseResponse<T>> = response
    fun onError(): LiveData<String> = error
    fun onLoading(): LiveData<Boolean> = isLoading

    abstract fun getDisposable(): CompositeDisposable?

    fun getBaseConsumer() = object : BaseConsumer<T>(){
        override fun isLoadingObserver(): MutableLiveData<Boolean> = isLoading
        override fun responseObserver(): MutableLiveData<BaseResponse<T>> = response
        override fun errorObserver(): MutableLiveData<String> = error
    }

    fun showLoading() {
        isLoading.value = true
    }

    override fun onCleared() {
        super.onCleared()
        getDisposable()?.clear()
    }

}
