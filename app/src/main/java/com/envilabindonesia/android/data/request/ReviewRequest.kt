package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-05-26.
 */

data class ReviewRequest(
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("orderId")
    val orderId: Int?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("serviceId")
    val serviceId: Int?
)