package com.envilabindonesia.android.data.request

import android.graphics.Bitmap
import android.net.Uri

/**
 * Created by galihadityo on 2019-04-11.
 */

data class ItemAttachment(
    var uri: Uri? = null,
    var bitmap: Bitmap? = null)