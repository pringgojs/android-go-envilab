package com.envilabindonesia.android.ui.intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.envilabindonesia.android.R
import com.envilabindonesia.android.data.response.OnboardingResponse
import com.envilabindonesia.android.extension.load
import kotlinx.android.synthetic.main.item_intro.view.*

/**
 * Created by galihadityo on 2019-06-13.
 */

class IntroAdapter(private val list: List<OnboardingResponse>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = LayoutInflater.from(container.context).inflate(R.layout.item_intro, container, false)
        list[position].url?.let { item.iv.load(it) }
        container.addView(item)
        return item
    }

    override fun isViewFromObject(view: View, data: Any): Boolean {
        return view == data
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, data: Any) {
        container.removeView(data as View)
    }

}