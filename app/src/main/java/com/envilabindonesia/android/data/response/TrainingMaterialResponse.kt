package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-14.
 */

data class TrainingMaterialResponse(
    @SerializedName("content")
    val content: String?,
    @SerializedName("file")
    val `file`: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastUpdate")
    val lastUpdate: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)