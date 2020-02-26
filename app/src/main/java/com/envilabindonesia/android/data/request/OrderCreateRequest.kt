package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-05-11.
 */

data class OrderCreateRequest(
    @SerializedName("companyId")
    val companyId: String?,
    @SerializedName("cpId")
    val cpId: String? = null,
    @SerializedName("services")
    val orderService: List<OrderServices>? = null,
    @SerializedName("notes")
    val notes: String? = null
)

data class OrderServices(
    @SerializedName("serviceId")
    val serviceId: Int?,
    @SerializedName("serviceNodes")
    val serviceNodes: Int?,
    @SerializedName("serviceNotes")
    val serviceNotes: String?
)