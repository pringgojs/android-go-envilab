package com.envilabindonesia.android.base.interceptors

import com.envilabindonesia.android.BuildConfig
import com.envilabindonesia.android.data.Constant
import com.envilabindonesia.android.data.response.LoginResponse
import com.envilabindonesia.android.util.PrefsUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by galihadityo on 2019-04-10.
 */

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var clientId = BuildConfig.clientId
        var clientKey = BuildConfig.clientKey

        PrefsUtil.getLoginResponse()?.let { response ->
            clientId = response.clientId ?: ""
            clientKey = response.clientKey ?: ""
        }

        val original = chain.request()
        val requestBuilder = original.newBuilder().addHeader("Content-type", "application/json")

        val originalHttpUrl = original.url()
        val urlBuilder = originalHttpUrl.newBuilder()

        Constant.GeneralAuth.forEach {
            if (original.url().toString().contains(it)) {
                clientId = BuildConfig.clientId
                clientKey = BuildConfig.clientKey
            }
        }

        requestBuilder.addHeader("clientId", clientId)
        requestBuilder.addHeader("clientKey", clientKey)

        val request = requestBuilder.url(urlBuilder.build()).build()
        return chain.proceed(request)
    }

}