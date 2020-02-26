package com.envilabindonesia.android.di.module

import com.envilabindonesia.android.ui.client.add.ClientAddActivity
import com.envilabindonesia.android.ui.client.contact.ContactPersonActivity
import com.envilabindonesia.android.ui.client.contact.list.ContactPersonListActivity
import com.envilabindonesia.android.ui.client.edit.ClientEditActivity
import com.envilabindonesia.android.ui.client.search.SearchClientActivity
import com.envilabindonesia.android.ui.client.search.SearchClientFragment
import com.envilabindonesia.android.ui.contact.detail.DetailContactActivity
import com.envilabindonesia.android.ui.forgot.ForgotPasswordActivity
import com.envilabindonesia.android.ui.home.HomeActivity
import com.envilabindonesia.android.ui.intro.IntroActivity
import com.envilabindonesia.android.ui.login.LoginActivity
import com.envilabindonesia.android.ui.notification.detail.NotificationDetailActivity
import com.envilabindonesia.android.ui.order.add.OrderAddActivity
import com.envilabindonesia.android.ui.order.detail.OrderDetailActivity
import com.envilabindonesia.android.ui.order.list.item.OrderListPerItemFragment
import com.envilabindonesia.android.ui.order.search.OrderSearchActivity
import com.envilabindonesia.android.ui.order.search.OrderSearchFragment
import com.envilabindonesia.android.ui.register.RegisterActivity
import com.envilabindonesia.android.ui.review.ReviewListActivity
import com.envilabindonesia.android.ui.schedule.list.ScheduleSamplingListActivity
import com.envilabindonesia.android.ui.search.SearchActivity
import com.envilabindonesia.android.ui.search.SearchFragment
import com.envilabindonesia.android.ui.services.search.SearchServiceActivity
import com.envilabindonesia.android.ui.services.search.SearchServiceFragment
import com.envilabindonesia.android.ui.splash.SplashActivity
import com.envilabindonesia.android.ui.verification.VerificationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by galihadityo on 2019-03-02.
 */

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindIntroActivity(): IntroActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindClientFormActivity(): ClientAddActivity

    @ContributesAndroidInjector
    abstract fun bindClientEditActivity(): ClientEditActivity

    @ContributesAndroidInjector
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindOrderAddActivity(): OrderAddActivity

    @ContributesAndroidInjector
    abstract fun bindOrderDetailActivity(): OrderDetailActivity

    @ContributesAndroidInjector
    abstract fun bindVerificationActivity(): VerificationActivity

    @ContributesAndroidInjector
    abstract fun bindForgotPasswordActivity(): ForgotPasswordActivity

    @ContributesAndroidInjector
    abstract fun bindSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun searchClientActivity(): SearchClientActivity

    @ContributesAndroidInjector
    abstract fun searchClientFragment(): SearchClientFragment

    @ContributesAndroidInjector
    abstract fun contactPersonActivity(): ContactPersonActivity

    @ContributesAndroidInjector
    abstract fun contactPersonListActivity(): ContactPersonListActivity

    @ContributesAndroidInjector
    abstract fun notificationDetailActivity(): NotificationDetailActivity

    @ContributesAndroidInjector
    abstract fun detailContactActivity(): DetailContactActivity

    @ContributesAndroidInjector
    abstract fun searchServiceActivity(): SearchServiceActivity

    @ContributesAndroidInjector
    abstract fun searchServiceFragment(): SearchServiceFragment

    @ContributesAndroidInjector
    abstract fun orderListPerItemFragment(): OrderListPerItemFragment

    @ContributesAndroidInjector
    abstract fun orderSearchActivity(): OrderSearchActivity

    @ContributesAndroidInjector
    abstract fun orderSearchFragment(): OrderSearchFragment

    @ContributesAndroidInjector
    abstract fun reviewListActivity(): ReviewListActivity

    @ContributesAndroidInjector
    abstract fun scheduleSamplingListActivity(): ScheduleSamplingListActivity

}