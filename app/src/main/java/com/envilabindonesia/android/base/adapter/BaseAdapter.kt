package com.envilabindonesia.android.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by galihadityo on 2019-04-11.
 */

class BaseAdapter<T, VH: RecyclerView.ViewHolder>(private val dataHolder: BaseViewHolder<T, VH>, private val onClick: (T) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: ArrayList<BaseAdapterModel<T>> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return dataHolder.createViewHolder(parent)
    }

    override fun getItemCount(): Int = mList.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        dataHolder.bindView(holder as VH, mList[position], onClick)
    }

    fun addAll(listData: ArrayList<BaseAdapterModel<T>>) {
        mList.clear()
        mList.addAll(listData).also { notifyDataSetChanged() }
    }

    fun clear() {
        mList.clear().also { notifyDataSetChanged() }
    }

}