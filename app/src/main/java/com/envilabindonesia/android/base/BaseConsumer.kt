package com.envilabindonesia.android.base

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.reactivex.functions.BiConsumer
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by galihadityo on 2019-03-26.
 */

abstract class BaseConsumer<T> : BiConsumer<Response<BaseResponse<T>>, Throwable> {

    abstract fun isLoadingObserver(): MutableLiveData<Boolean>
    abstract fun responseObserver(): MutableLiveData<BaseResponse<T>>
    abstract fun errorObserver(): MutableLiveData<String>

    companion object {
        const val errorGeneral = "Sistem bermasalah, coba beberapa saat lagi"
        const val errorConnection = "Periksa koneksi internet anda"
        const val errorTimeOut = "Server membutuhkan waktu lebih lama, coba beberapa saat lagi"

        fun parseError(responseBody: ResponseBody?): String {
            responseBody?.let {
                try {
                    return Gson().fromJson(it.string(), BaseResponse::class.java).message ?: errorGeneral
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return errorGeneral
        }

        fun parseError(throwable: Throwable): String {
            throwable.printStackTrace()
            return when (throwable) {
                is UnknownHostException -> errorConnection
                is SocketTimeoutException -> errorTimeOut
                else -> errorGeneral
            }
        }
    }

    override fun accept(t1: Response<BaseResponse<T>>?, t2: Throwable?) {
        isLoadingObserver().value = false
        t1?.let {
            if (it.isSuccessful) {
                val response = it.body()
                if (response != null) {
                    if (response.error == false) {
                        responseObserver().value = response
                    } else {
                        errorObserver().value = response.message ?: errorGeneral
                    }
                } else {
                    errorObserver().value = errorGeneral
                }
            } else {
                errorObserver().value = parseError(it.errorBody())
            }
        }

        t2?.let {
            errorObserver().value = parseError(it)
        }
    }

}