package com.envilabindonesia.android.ui.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.adapter.BaseAdapterModel
import com.envilabindonesia.android.data.response.TrainingMaterialResponse
import com.envilabindonesia.android.extension.load
import kotlinx.android.synthetic.main.item_training_video.view.*

/**
 * Created by galihadityo on 2019-04-11.
 */

class TrainingVideoAdapter<T>(private val height: Int, private val onClick: (T) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: ArrayList<BaseAdapterModel<T>> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder<T>(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_training_video, parent, false)
        )

    override fun getItemCount(): Int = mList.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as ViewHolder<T>) {
            bind(mList[position], onClick, height)
        }
    }

    fun addAll(listData: ArrayList<BaseAdapterModel<T>>) {
        mList.clear()
        mList.addAll(listData).also { notifyDataSetChanged() }
    }

    class ViewHolder<T>(val view: View): RecyclerView.ViewHolder(view) {

        fun bind(
            data: BaseAdapterModel<T>,
            onClick: (T) -> Unit,
            height: Int
        ) {
            val training = data.data as TrainingMaterialResponse

            view.iv.minimumHeight = height
            view.tv.text = data.title
            view.layout.setOnClickListener { onClick(data.data) }
            view.iv.load(training.thumbnail ?: return)
        }

    }
}