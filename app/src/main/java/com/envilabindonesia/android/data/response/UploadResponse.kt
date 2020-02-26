package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-11.
 */

data class UploadResponse(
    @SerializedName("fileUrl")
    val fileUrl: String?
)