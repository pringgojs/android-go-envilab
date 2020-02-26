package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-03-26.
 */

data class ForgotPasswordRequest(
    @SerializedName("email")
    val email: String?
)