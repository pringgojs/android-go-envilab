package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-05-11.
 */

data class OrderCancelRequest(
    @SerializedName("orderId")
    val orderId: Int?,
    @SerializedName("reason")
    val reason: String?
)