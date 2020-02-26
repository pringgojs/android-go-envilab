package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-20.
 */

data class CompanyAddRequest(
    @SerializedName("companyAddress")
    val companyAddress: String?,
    @SerializedName("companyFax")
    val companyFax: String?,
    @SerializedName("companyName")
    val companyName: String?,
    @SerializedName("companyPhone")
    val companyPhone: String?,
    @SerializedName("districtId")
    val districtId: Int?
)