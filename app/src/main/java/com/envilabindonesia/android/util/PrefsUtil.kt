package com.envilabindonesia.android.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.envilabindonesia.android.data.response.LoginResponse
import com.envilabindonesia.android.data.response.OnboardingResponse
import com.envilabindonesia.android.data.response.TransactionStatusResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by galihadityo on 2019-03-26.
 */

object PrefsUtil {

    const val LOGIN = "LOGIN"
    const val ONBOARDING = "ONBOARDING"
    const val STATUS_TRANSACTION = "STATUS_TRANSACTION"
    const val FCM_TOKEN = "FCM_TOKEN"
    const val ORDER_IS_SUBORDINATE = "ORDER_IS_SUBORDINATE"
    const val YOUTUBE_ID = "YOUTUBE_ID"

    const val SHOWCASE_HOME_ORDER = "SHOWCASE_HOME_ORDER"
    const val SHOWCASE_ORDER_SERVICE = "SHOWCASE_ORDER_SERVICE"
    const val SHOWCASE_CREATE_CONTACT = "SHOWCASE_CREATE_CONTACT"
    const val SHOWCASE_CREATE_COMPANY = "SHOWCASE_CREATE_COMPANY"
    const val SHOWCASE_EDIT_COMPANY = "SHOWCASE_EDIT_COMPANY"
    const val SHOWCASE_SUPPORT_CALL = "SHOWCASE_SUPPORT_CALL"
    const val SHOWCASE_SUPPORT_EMAIL = "SHOWCASE_SUPPORT_EMAIL"
    const val SHOWCASE_SUPPORT_CHAT = "SHOWCASE_SUPPORT_CHAT"
    const val SHOWCASE_SUPPORT_WA = "SHOWCASE_SUPPORT_WA"
    const val SHOWCASE_DASHBOARD_PERFORMA_MONTH = "SHOWCASE_DASHBOARD_PERFORMA_MONTH"

    private var sharedPreferences: SharedPreferences? = null
    private var sharedPreferencesMaster: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        if (sharedPreferencesMaster == null) sharedPreferencesMaster = context.getSharedPreferences("master_data_envilab", Activity.MODE_PRIVATE)
    }

    fun read(key: String?, defaultValue: String?): String? = sharedPreferences?.getString(key, defaultValue)

    fun write(key: String?, defaultValue: String?) {
        var editor: SharedPreferences.Editor = sharedPreferences?.edit() ?: return
        editor.putString(key, defaultValue)
        editor.apply()
    }

    fun write(key: String?, defaultValue: Int) {
        var editor: SharedPreferences.Editor = sharedPreferences?.edit() ?: return
        editor.putInt(key, defaultValue)
        editor.apply()
    }

    fun <T> write(key: String?, value: T?) {
        val data = Gson().toJson(value)
        val editor: SharedPreferences.Editor = sharedPreferences?.edit() ?: return
        editor.putString(key, data)
        editor.apply()
    }

    fun <T> read(key: String?, classOf: Class<T>): T? {
        val data = sharedPreferences?.getString(key, "")
        if (data?.isEmpty() == true) return null
        return Gson().fromJson(data, classOf)
    }

    fun <T> writeMaster(key: String?, value: T?) {
        val data = Gson().toJson(value)
        val editor: SharedPreferences.Editor = sharedPreferencesMaster?.edit() ?: return
        editor.putString(key, data)
        editor.apply()
    }

    fun <T> readMaster(key: String?, classOf: Class<T>): T? {
        val data = sharedPreferencesMaster?.getString(key, "")
        if (data?.isEmpty() == true) return null
        return Gson().fromJson(data, classOf)
    }

    fun <T> readMaster(key: String?): List<T>? {
        val data = sharedPreferencesMaster?.getString(key, "")
        if (data?.isEmpty() == true) return null
        val typeToken = object : TypeToken<ArrayList<T>>() {}.type
        return Gson().fromJson(data, typeToken)
    }

    fun clearDatas() {
        sharedPreferences?.edit()?.clear()?.apply()
    }

    fun getLoginResponse(): LoginResponse? = read(PrefsUtil.LOGIN, LoginResponse::class.java)

    fun setLoginResponse(result: LoginResponse?) {
        PrefsUtil.write(PrefsUtil.LOGIN, result)
    }

    fun setFcmToken(token: String) {
        val editor: SharedPreferences.Editor = sharedPreferences?.edit() ?: return
        editor.putString(FCM_TOKEN, token)
        editor.apply()
    }

    fun getFcmToken(): String {
        return sharedPreferences?.getString(FCM_TOKEN, "") ?: ""
    }

    fun setTransactionStatus(result: List<TransactionStatusResponse>?) {
        if (result == null) return
        val editor: SharedPreferences.Editor = sharedPreferencesMaster?.edit() ?: return
        editor.putString(STATUS_TRANSACTION, Gson().toJson(result))
        editor.apply()
    }

    fun getTransactionStatus(): List<TransactionStatusResponse> {
        val data = sharedPreferencesMaster?.getString(STATUS_TRANSACTION, "")
        return if (data?.isEmpty() == false) {
            val type = object : TypeToken<List<TransactionStatusResponse>>() {}.type
            Gson().fromJson(data, type)
        } else {
            listOf()
        }
    }

    fun setOnboarding(result: List<OnboardingResponse>?) {
        if (result == null) return
        val editor: SharedPreferences.Editor = sharedPreferencesMaster?.edit() ?: return
        editor.putString(ONBOARDING, Gson().toJson(result))
        editor.apply()
    }

    fun getOnboarding(): List<OnboardingResponse> {
        val data = sharedPreferencesMaster?.getString(ONBOARDING, "")
        return if (data?.isEmpty() == false) {
            val type = object : TypeToken<List<OnboardingResponse>>() {}.type
            Gson().fromJson(data, type)
        } else {
            listOf()
        }
    }

    fun getTransactionStatusColor(status: Int): String {
        return getTransactionStatus().find { it.statusId == status }?.statusHexColor ?: "#FF0000"
    }

    fun isCurrencyShow(): Boolean {
        return if (getTransactionStatus().isNotEmpty()) {
            getTransactionStatus()[0].showCurrency ?: false
        } else {
            false
        }
    }

    fun appVersion(): Int {
        return if (getTransactionStatus().isNotEmpty()) {
            getTransactionStatus()[0].androidVersion?.toInt() ?: 0
        } else {
            0
        }
    }

    fun isOrderSubordinate(): Boolean {
        return read(PrefsUtil.ORDER_IS_SUBORDINATE, Boolean::class.java) ?: false
    }

}