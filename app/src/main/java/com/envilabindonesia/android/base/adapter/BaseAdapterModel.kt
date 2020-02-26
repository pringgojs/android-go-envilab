package com.envilabindonesia.android.base.adapter

import androidx.annotation.DrawableRes

/**
 * Created by galihadityo on 2019-04-14.
 */

data class BaseAdapterModel<T>(
    val title: String? = null,
    val data: T? = null,
    @DrawableRes val iconLeft: Int? = null,
    @DrawableRes val iconRight: Int? = null,
    val isLoading: Boolean? = false
)