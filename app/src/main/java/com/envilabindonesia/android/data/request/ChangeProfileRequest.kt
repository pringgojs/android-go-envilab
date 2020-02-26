package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-18.
 */

data class ChangeProfileRequest(
    @SerializedName("address")
    val address: String?,
    @SerializedName("districtId")
    val districtId: String?,
    @SerializedName("docUrl1")  // NPWP
    val docUrl1: String?,
    @SerializedName("docUrl2")  // KTP/SPPKP
    val docUrl2: String?,
    @SerializedName("profileImage")  // photo
    val profileImage: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("noKTP")
    val noKTP: String?,
    @SerializedName("noNPWP")
    val noNPWP: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("companyId")
    var companyId: Int? = null
)