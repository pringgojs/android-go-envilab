package com.envilabindonesia.android.base

import com.google.gson.annotations.SerializedName

/**
 * Created by galihadityo on 2019-04-10.
 */

class BasePageResponse<T> (
    @SerializedName("endIndex")
    val endIndex: Int?,
    @SerializedName("numFound")
    val numFound: Int?,
    @SerializedName("rows")
    val rows: List<T>?,
    @SerializedName("startIndex")
    val startIndex: Int?
)