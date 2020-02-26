package com.envilabindonesia.android.ui.splash

import android.content.Intent
import com.envilabindonesia.android.BuildConfig
import com.envilabindonesia.android.R
import com.envilabindonesia.android.base.BaseActivity
import com.envilabindonesia.android.base.BaseObserver
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.extension.getViewModel
import com.envilabindonesia.android.ui.home.HomeActivity
import com.envilabindonesia.android.ui.home.HomeViewModel
import com.envilabindonesia.android.ui.intro.IntroActivity
import com.envilabindonesia.android.util.PrefsUtil
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { getViewModel<SplashViewModel>(viewModelFactory) }
    private val viewModelHome by lazy { getViewModel<HomeViewModel>(viewModelFactory) }

    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun init() {
        tvVersion.text = String.format("v${BuildConfig.VERSION_NAME}")
        nextActivity()
    }

    private fun next() {
        if (PrefsUtil.getLoginResponse() == null) {
            startActivity(Intent(applicationContext, IntroActivity::class.java))
        } else {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }
        finish()
    }

    private fun nextActivity() {
        if (PrefsUtil.getTransactionStatus().isEmpty()) viewModelHome.getMasterDataSplash()
        viewModel.getOnBoarding()
        viewModel.onSuccess().observe(this, BaseObserver{
            PrefsUtil.setOnboarding(it.result?.rows)
            next()
        })

        viewModel.onError().observe(this, BaseObserver {
            next()
        })
    }
}