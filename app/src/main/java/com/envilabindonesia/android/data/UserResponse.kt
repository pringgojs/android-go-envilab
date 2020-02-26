package com.envilabindonesia.android.data
import com.google.gson.annotations.SerializedName

/**
 * Created by galihadityo on 2019-03-02.
 */

data class UserResponse(
    @SerializedName("body")
    val body: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?
)