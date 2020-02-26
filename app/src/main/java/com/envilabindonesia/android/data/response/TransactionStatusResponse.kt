package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-05-11.
 */

@Parcelize
data class TransactionStatusResponse(
    @SerializedName("statusId")
    val statusId: Int?,
    @SerializedName("statusName")
    val statusName: String?,
    @SerializedName("statusHexColor")
    val statusHexColor: String?,
    @SerializedName("androidVersion")
    val androidVersion: String?,
    @SerializedName("showCurrency")
    val showCurrency: Boolean?
): Parcelable