package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-04-21.
 */

@Parcelize
data class ContactPersonResponse(
    @SerializedName("companyId")
    val companyId: Int? = 0,
    @SerializedName("companyName")
    val companyName: String? = "",
    @SerializedName("cpEmail")
    val cpEmail: String? = "",
    @SerializedName("cpId")
    val cpId: Int? = 0,
    @SerializedName("cpName")
    val cpName: String? = "",
    @SerializedName("cpPhone")
    val cpPhone: String? = "",
    @SerializedName("cpPosition")
    val cpPosition: String? = ""
): Parcelable