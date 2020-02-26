package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by galihadityo on 2019-04-20.
 */

@Parcelize
data class ServiceCorporateResponse(
    @SerializedName("catId")
    val catId: Int? = null,
    @SerializedName("catName")
    var catName: String? = null,
    @SerializedName("serviceId")
    val serviceId: Int? = null,
    @SerializedName("serviceParameter")
    val serviceParameter: String? = null,
    @SerializedName("servicePrice")
    val servicePrice: Int? = null,
    @SerializedName("serviceRegulation")
    val serviceRegulation: String? = null,
    @SerializedName("serviceNodes")
    var serviceNodes: Int? = 1,
    @SerializedName("serviceNotes")
    var serviceNotes: String? = ""
): Parcelable