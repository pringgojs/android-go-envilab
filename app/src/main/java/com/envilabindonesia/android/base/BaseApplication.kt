package com.envilabindonesia.android.base

import android.content.Context
import androidx.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.envilabindonesia.android.BuildConfig
import com.envilabindonesia.android.di.component.DaggerApplicationComponent
import com.envilabindonesia.android.util.PrefsUtil
import com.google.firebase.FirebaseApp
import com.zopim.android.sdk.api.ZopimChat
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric
import timber.log.Timber

/**
 * Created by galihadityo on 2019-03-02.
 */

class BaseApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
        return component
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        FirebaseApp.initializeApp(this)
        Fabric.with(this, Crashlytics())
        PrefsUtil.init(this)
        ZopimChat.init(BuildConfig.zopimKey)
    }

}