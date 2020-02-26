package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-09.
 */

data class ChangePasswordResponse(
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("userId")
    val userId: Int?
)