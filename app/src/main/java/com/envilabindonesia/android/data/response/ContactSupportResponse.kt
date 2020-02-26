package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-04-28.
 */

@Parcelize
data class ContactSupportResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("branchname")
    val branchname: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fax")
    val fax: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("whatsapp")
    val whatsapp: String?,
    @SerializedName("note")
    val note: String?
): Parcelable