package com.envilabindonesia.android.util

import android.content.res.Resources

/**
 * Created by galihadityo on 2019-04-20.
 */

object DeviceUtil {

    fun getHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun getWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

}