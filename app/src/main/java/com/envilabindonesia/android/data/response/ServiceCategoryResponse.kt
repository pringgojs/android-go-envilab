package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-04-20.
 */

@Parcelize
data class ServiceCategoryResponse(
    @SerializedName("catId")
    val catId: Int?,
    @SerializedName("catName")
    val catName: String?
): Parcelable