package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-03-26.
 */

data class ChangePasswordRequest(
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("oldPassword")
    val oldPassword: String?,
    @SerializedName("retypePassword")
    val retypePassword: String?
)