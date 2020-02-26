package com.envilabindonesia.android.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by galihadityo on 2019-04-16.
 */

abstract class BaseViewHolder<T, VH : RecyclerView.ViewHolder> {
    abstract fun bindView(holder: VH, data: BaseAdapterModel<T>, onClick: (T) -> Unit)
    abstract fun createViewHolder(parent: ViewGroup): VH
}