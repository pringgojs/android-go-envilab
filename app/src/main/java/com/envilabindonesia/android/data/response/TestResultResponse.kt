package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-05-26.
 */

@Parcelize
data class TestResultResponse(
    @SerializedName("hardcopy")
    val hardcopy: Boolean?,
    @SerializedName("logisticResi")
    val logisticResi: String?,
    @SerializedName("logisticVendor")
    val logisticVendor: String?,
    @SerializedName("pdfUrl")
    val pdfUrl: String?,
    @SerializedName("softcopy")
    val softcopy: Boolean?
): Parcelable