package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-03-26.
 */

@Parcelize
data class ProvincesResponse(
    @SerializedName("provinceId")
    var provinceId: Int?,
    @SerializedName("provinceName")
    var provinceName: String?
):Parcelable