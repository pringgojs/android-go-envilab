package com.envilabindonesia.android.di.module

import android.content.Context
import com.envilabindonesia.android.base.BaseApplication
import dagger.Binds
import dagger.Module

/**
 * Created by galihadityo on 2019-03-02.
 */

@Module
abstract class ContextModule {

    @Binds
    abstract fun provideContext(application: BaseApplication): Context

}