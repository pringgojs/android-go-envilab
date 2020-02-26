package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-14.
 */

data class StaticPagesResponse(
    @SerializedName("content")
    val content: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastUpdate")
    val lastUpdate: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("slug")
    val slug: String?
)