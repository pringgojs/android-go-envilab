package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-07.
 */

data class MemberStatusResponse(
    @SerializedName("documentCompletion")
    val documentCompletion: Boolean?,
    @SerializedName("isVerified")
    val isVerified: Boolean?,
    @SerializedName("memberStatusLabel")
    val memberStatusLabel: String?,
    @SerializedName("enableOrder")
    val enableOrder: Boolean?,
    @SerializedName("enableOrderMessage")
    val enableOrderMessage: String?
)