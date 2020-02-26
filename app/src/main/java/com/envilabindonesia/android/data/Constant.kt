package com.envilabindonesia.android.data

import android.content.Context
import com.envilabindonesia.android.R
import com.envilabindonesia.android.ui.register.RegisterActivity
import com.envilabindonesia.android.util.PrefsUtil

/**
 * Created by galihadityo on 2019-04-18.
 */

object Constant {

    val GeneralAuth = arrayOf(
        "master/provinces",
        "master/regencies",
        "master/districts",
        "master/statusorders?background=1")

    fun isLoginAsUnverified(): Boolean = PrefsUtil.getLoginResponse()?.isVerified == false

    fun isLoginAsMitraOrSales(): Boolean = PrefsUtil.getLoginResponse()?.registeredAs == RegisterActivity.ROLE_MITRA || PrefsUtil.getLoginResponse()?.registeredAs == RegisterActivity.ROLE_SALES

    fun isLoginAsMitra(): Boolean = PrefsUtil.getLoginResponse()?.registeredAs == RegisterActivity.ROLE_MITRA

    fun isLoginAsSales(): Boolean = PrefsUtil.getLoginResponse()?.registeredAs == RegisterActivity.ROLE_SALES

    fun isLoginAsCompany(): Boolean = PrefsUtil.getLoginResponse()?.registeredAs == RegisterActivity.ROLE_COMPANY

    object ActivityResult {
        const val RC_VERIFY = 1
        const val RC_CAMERA_NPWP = 2
        const val RC_GALLERY_NPWP = 3
        const val RC_CAMERA_SKKPK = 4
        const val RC_GALLERY_SKKPK = 5
        const val RC_CAMERA = 6
        const val RC_GALLERY = 7
        const val RC_PROVINCES = 8
        const val RC_DISTRICTS = 9
        const val RC_REGENCIES = 10
        const val RC_SEARCH_CLIENT = 11
        const val RC_CLIENT_ADD = 12
        const val RC_SEARCH_SERVICE = 13
        const val RC_SEARCH_CONTACT_PERSON = 14
        const val RC_SEARCH_ORDER = 15
        const val RC_EDIT_ORDER = 16
    }

    object TransactionStatus {
        const val REQUEST = 1
        const val COMPLETED = 3
    }

    object Review {
        fun getStatus(context: Context, rating: Float): String {
            return when (rating) {
                1f -> context.getString(R.string.rate_poor)
                2f -> context.getString(R.string.rate_bad)
                3f -> context.getString(R.string.rate_standar)
                4f -> context.getString(R.string.rate_good)
                5f -> context.getString(R.string.rate_awesome)
                else -> ""
            }
        }
    }

}