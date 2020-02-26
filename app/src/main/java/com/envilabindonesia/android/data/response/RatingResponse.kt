package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-05-25.
 */

data class RatingResponse(
    @SerializedName("rate")
    val rate: List<Rate>?,
    @SerializedName("rateAccess")
    val rateAccess: Boolean?
)

@Parcelize
data class Rate(
    @SerializedName("catName")
    val catName: String?,
    @SerializedName("rate")
    val rate: Int?,
    @SerializedName("rateComment")
    val rateComment: String?,
    @SerializedName("rateDate")
    val rateDate: String?,
    @SerializedName("rateId")
    val rateId: Int?,
    @SerializedName("rated")
    val rated: Boolean?,
    @SerializedName("serviceId")
    val serviceId: Int?,
    @SerializedName("serviceParameter")
    val serviceParameter: String?,
    @SerializedName("serviceRegulation")
    val serviceRegulation: String?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("userFullName")
    val userFullName: String?
): Parcelable