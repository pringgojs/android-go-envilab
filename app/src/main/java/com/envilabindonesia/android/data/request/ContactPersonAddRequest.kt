package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-21.
 */

data class ContactPersonAddRequest(
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("cpEmail")
    val cpEmail: String?,
    @SerializedName("cpName")
    val cpName: String?,
    @SerializedName("cpPhone")
    val cpPhone: String?,
    @SerializedName("cpPosition")
    val cpPosition: String?
)