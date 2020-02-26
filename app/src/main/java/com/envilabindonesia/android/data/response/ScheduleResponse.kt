package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-05-21.
 */

@Parcelize
data class ScheduleResponse(
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("dayOff")
    val dayOff: String? = null,
    @SerializedName("isDayOff")
    val isDayOff: Boolean? = null,
    @SerializedName("orderFound")
    val orderFound: Int? = null,
    @SerializedName("orders")
    val orders: List<TransactionResponse>? = null,
    val day: String? = ""
): Parcelable