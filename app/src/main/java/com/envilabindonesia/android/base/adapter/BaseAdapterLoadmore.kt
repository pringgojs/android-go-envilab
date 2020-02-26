package com.envilabindonesia.android.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R

/**
 * Created by galihadityo on 2019-04-11.
 */

class BaseAdapterLoadmore<T, VH: RecyclerView.ViewHolder>(private val onClick: (T) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: ArrayList<BaseAdapterModel<T>> = arrayListOf()

    companion object {
        const val ITEM_LOADING = 0
        const val ITEM_DATA = 1
    }

    fun setDataViewHolder(dataHolder: BaseViewHolderLoadmore<T, VH>) {
        this.dataHolder = dataHolder
    }

    private lateinit var dataHolder: BaseViewHolderLoadmore<T, VH>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_LOADING) {
            ProgessHolder(
                v.inflate(
                    R.layout.item_base_loadmore_progress,
                    parent,
                    false
                )
            )
        } else {
            dataHolder.createViewHolder(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position].isLoading == true) ITEM_LOADING else ITEM_DATA
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_DATA) {
            @Suppress("UNCHECKED_CAST")
            dataHolder.bindView(holder as VH, mList[position], onClick)
            //            (holder as BaseLoadmoreViewHolder<T>).bind(mList[position], onClick)
        } else {
            (holder as ProgessHolder)
        }
    }

    fun showLoadmore() {
        mList.add(BaseAdapterModel(isLoading = true))
        notifyItemInserted(mList.size.minus(1))
    }

    fun hideLoadmore() {
        mList.removeAt(mList.size.minus(1))
        notifyItemRemoved(mList.size)
    }

    fun clear() {
        mList.clear().also { notifyDataSetChanged() }
    }

    fun add(listData: ArrayList<BaseAdapterModel<T>>) {
        mList.addAll(listData).also { notifyDataSetChanged() }
    }

    class ProgessHolder(val view: View): RecyclerView.ViewHolder(view)

}