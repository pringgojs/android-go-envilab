package com.envilabindonesia.android.ui.intro

import android.content.Intent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.ui.login.LoginActivity
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_intro

    override fun init() {
        val datas = PrefsUtil.getOnboarding()
        viewpager.adapter = IntroAdapter(datas)
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (datas.isEmpty()) {
                    btnSkip.visibility = View.VISIBLE
                    return
                }

                btnSkip.visibility = if (position == datas.size.minus(1)) View.VISIBLE else View.INVISIBLE
            }
        })

        indicator.setViewPager(viewpager)
        btnSkip.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

}