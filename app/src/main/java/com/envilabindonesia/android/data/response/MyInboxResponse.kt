package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by galihadityo on 2019-04-28.
 */

@Parcelize
data class MyInboxResponse(
    @SerializedName("created")
    val created: String?,
    @SerializedName("inboxId")
    val inboxId: Int?,
    @SerializedName("isRead")
    val isRead: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("subject")
    val subject: String?,
    @SerializedName("userId")
    val userId: Int?
): Parcelable