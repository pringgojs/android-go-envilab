package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-03-26.
 */


data class LoginResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("clientId")
    val clientId: String?,
    @SerializedName("clientKey")
    val clientKey: String?,
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("companyName")
    val companyName: String?,
    @SerializedName("districtId")
    val districtId: Int?,
    @SerializedName("districtName")
    val districtName: String?,
    @SerializedName("docUrl1")
    val docUrl1: String?,
    @SerializedName("docUrl2")
    val docUrl2: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("isVerified")
    val isVerified: Boolean?,
    @SerializedName("lastLogin")
    val lastLogin: String?,
    @SerializedName("lastUpdate")
    val lastUpdate: String?,
    @SerializedName("noKTP")
    val noKTP: String?,
    @SerializedName("noNPWP")
    val noNPWP: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("profileImageReady")
    val profileImageReady: Boolean?,
    @SerializedName("provinceId")
    val provinceId: Int?,
    @SerializedName("provinceName")
    val provinceName: String?,
    @SerializedName("regencyId")
    val regencyId: Int?,
    @SerializedName("regencyName")
    val regencyName: String?,
    @SerializedName("registerDate")
    val registerDate: String?,
    @SerializedName("registeredAs")
    var registeredAs: Int?,
    @SerializedName("registeredAsLabel")
    val registeredAsLabel: String?,
    @SerializedName("userId")
    val userId: Int?
)