package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-04-11.
 */

data class FAQResponse(
    @SerializedName("answer")
    val answer: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastUpdate")
    val lastUpdate: String?,
    @SerializedName("question")
    val question: String?
)