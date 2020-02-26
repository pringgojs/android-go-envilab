package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-04-10.
 */

@Parcelize
data class RegencyResponse(
    @SerializedName("provinceId")
    val provinceId: Int?,
    @SerializedName("provinceName")
    val provinceName: String?,
    @SerializedName("regencyId")
    val regencyId: Int?,
    @SerializedName("regencyName")
    val regencyName: String?
): Parcelable