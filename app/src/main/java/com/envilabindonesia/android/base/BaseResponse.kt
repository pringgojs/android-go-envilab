package com.envilabindonesia.android.base
import com.google.gson.annotations.SerializedName

/**
 * Created by galihadityo on 2019-03-25.
 */

class BaseResponse<T>(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("allowLoggedIn")
    val allowLoggedIn: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: T?
)