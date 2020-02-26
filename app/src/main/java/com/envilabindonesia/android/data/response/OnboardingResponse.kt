package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-10.
 */

data class OnboardingResponse(
    @SerializedName("filetype")
    val filetype: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)