package com.envilabindonesia.android.ui.dashboard

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * Created by galihadityo on 2019-03-13.
 */

class DashboardOrderPageAdapter(private val views: List<View>): PagerAdapter() {

    fun getViews(position: Int): View = views[position]

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = views.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = views[position]
        container.addView(view)
        return view
    }

    override fun getItemPosition(`object`: Any): Int {
        for (index in 0 until count) {
            if (`object` as View === views[index]) {
                return index
            }
        }
        return PagerAdapter.POSITION_NONE
    }

}