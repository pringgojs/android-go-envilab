package com.envilabindonesia.android.data

import com.google.gson.annotations.SerializedName

/**
 * Created by galihadityo on 2019-03-13.
 */

data class PickedServicesResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?
)