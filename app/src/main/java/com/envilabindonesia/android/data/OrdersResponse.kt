package com.envilabindonesia.android.data

import com.google.gson.annotations.SerializedName

/**
 * Created by galihadityo on 2019-03-13.
 */

data class OrdersResponse(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("status")
    val status: String? = null
)