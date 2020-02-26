package com.envilabindonesia.android.util

/**
 * Created by galihadityo on 2019-04-02.
 */

object PhoneUtil {

    const val AREA_PHONE = "+62"

    fun areaAdd(phone: String?): String {
        return "$AREA_PHONE$phone"
    }

    fun areaRemove(phone: String?): String {
        return phone?.replace(AREA_PHONE, "", true) ?: ""
    }

}