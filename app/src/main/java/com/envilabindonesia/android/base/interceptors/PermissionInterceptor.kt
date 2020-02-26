package com.envilabindonesia.android.base.interceptors

import android.util.Log
import com.envilabindonesia.android.extension.RxBus
import com.envilabindonesia.android.extension.RxEvent
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

/**
 * Created by galihadityo on 2019-04-10.
 */

class PermissionInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)
        if (response.isSuccessful) {
            try {
                if (response.body() != null) {
                    val responseBody = response.peekBody(Long.MAX_VALUE)
                    val objectResponse = JSONObject(responseBody.string())
                    val allowLoggedIn = objectResponse.getBoolean("allowLoggedIn")
                    val message = objectResponse.getString("message")
                    if (!allowLoggedIn) {
                        Log.i("Permission Interceptor", "Langsung Logout")
                        RxBus.post(RxEvent.Logout(message))
                    } else {
                        Log.i("Permission Interceptor", "Allowed gaes")
                    }
                }
            } catch (e: Exception) {
                Log.i("Permission Interceptor", "check class Permission Interceptor")
                e.printStackTrace()
            }
        }

        return response
    }

}