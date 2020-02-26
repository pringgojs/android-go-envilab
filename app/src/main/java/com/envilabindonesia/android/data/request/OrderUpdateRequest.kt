package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-05-11.
 */

data class OrderUpdateRequest(
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("orderId")
    val orderId: Int? = null,
    @SerializedName("cpId")
    val cpId: Int? = null,
    @SerializedName("services")
    val orderService: List<OrderServices>? = null,
    @SerializedName("notes")
    val notes: String? = null
)