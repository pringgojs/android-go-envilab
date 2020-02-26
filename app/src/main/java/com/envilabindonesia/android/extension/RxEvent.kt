package com.envilabindonesia.android.extension

import android.graphics.Bitmap
import android.net.Uri


/**
 * Created by galihadityo on 2019-04-10.
 */

class RxEvent {

    data class AppVersionChecker(val version: Int? = null)

    data class Logout(val message: String? = null)

    data class ImageBitmap(val bitmap: Bitmap? = null)

    data class ImageUri(val uri: Uri? = null)

    data class Profile(val rc: Int, val id: Int, val name: String)

}