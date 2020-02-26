package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-03-26.
 */

@Parcelize
data class RegisterResponse(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("fullName")
    val fullName: String? = null,
    @SerializedName("isActive")
    val isActive: Boolean? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("profileImage")
    val profileImage: String? = null,
    @SerializedName("profileImageReady")
    val profileImageReady: Boolean? = null,
    @SerializedName("registeredAs")
    val registeredAs: Int? = null,
    @SerializedName("registeredAsLabel")
    val registeredAsLabel: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
): Parcelable