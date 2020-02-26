package com.envilabindonesia.android.di.component

import android.app.Application
import com.envilabindonesia.android.base.BaseApplication
import com.envilabindonesia.android.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by galihadityo on 2019-03-02.
 */

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    ContextModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    ViewModelModule::class
])

interface ApplicationComponent: AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    override fun inject(baseApplication: BaseApplication)

}