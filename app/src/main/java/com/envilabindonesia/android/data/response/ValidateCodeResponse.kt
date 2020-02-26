package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-21.
 */

data class ValidateCodeResponse(
    @SerializedName("email")
    val email: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("userId")
    val userId: Int?
)