package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-04-16.
 */

@Parcelize
data class CompanyResponse(
    @SerializedName("companyAddress")
    val companyAddress: String? = "",
    @SerializedName("companyCode")
    val companyCode: String? = "",
    @SerializedName("companyFax")
    val companyFax: String? = "",
    @SerializedName("companyId")
    val companyId: Int? = 0,
    @SerializedName("companyName")
    val companyName: String? = "",
    @SerializedName("companyPhone")
    val companyPhone: String? = "",
    @SerializedName("districtId")
    val districtId: Int? = 0,
    @SerializedName("districtName")
    val districtName: String? = "",
    @SerializedName("provinceId")
    val provinceId: Int? = 0,
    @SerializedName("provinceName")
    val provinceName: String? = "",
    @SerializedName("regencyId")
    val regencyId: Int? = 0,
    @SerializedName("regencyName")
    val regencyName: String? = "",
    @SerializedName("isProperColorSet")
    val isProperColorSet: Boolean? = false,
    @SerializedName("properColor")
    val properColor: String? = "",
    @SerializedName("properLabel")
    val properLabel: String? = ""
): Parcelable