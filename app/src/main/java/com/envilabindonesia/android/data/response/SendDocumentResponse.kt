package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-06-03.
 */

data class SendDocumentResponse(
    @SerializedName("email")
    val email: String?,
    @SerializedName("type")
    val type: String?
)