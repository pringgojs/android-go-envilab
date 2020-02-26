package com.envilabindonesia.android.di.module

import com.envilabindonesia.android.ui.account.AccountFragment
import com.envilabindonesia.android.ui.account.password.PasswordFragment
import com.envilabindonesia.android.ui.account.profile.ProfileFragment
import com.envilabindonesia.android.ui.account.upload.UploadFragment
import com.envilabindonesia.android.ui.client.ClientFragment
import com.envilabindonesia.android.ui.contact.ContactFragment
import com.envilabindonesia.android.ui.dashboard.DashboardFragment
import com.envilabindonesia.android.ui.faq.FAQFragment
import com.envilabindonesia.android.ui.notification.NotificationFragment
import com.envilabindonesia.android.ui.order.list.OrderListFragment
import com.envilabindonesia.android.ui.performance.PerformanceFragment
import com.envilabindonesia.android.ui.schedule.ScheduleFragment
import com.envilabindonesia.android.ui.services.category.CorporateServiceCategoriesFragment
import com.envilabindonesia.android.ui.services.CorporateServiceFragment
import com.envilabindonesia.android.ui.training.TrainingMaterialFragment
import com.envilabindonesia.android.ui.training.TrainingPdfFragment
import com.envilabindonesia.android.ui.training.TrainingVideoFragment
import com.envilabindonesia.android.ui.webview.WebviewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by galihadityo on 2019-03-02.
 */

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun dashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun orderListFragment(): OrderListFragment

    @ContributesAndroidInjector
    abstract fun clientFragment(): ClientFragment

    @ContributesAndroidInjector
    abstract fun performanceFragment(): PerformanceFragment

    @ContributesAndroidInjector
    abstract fun accountFragment(): AccountFragment

    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun passwordFragment(): PasswordFragment

    @ContributesAndroidInjector
    abstract fun uploadFragment(): UploadFragment

    @ContributesAndroidInjector
    abstract fun contactFragment(): ContactFragment

    @ContributesAndroidInjector
    abstract fun faqFragment(): FAQFragment

    @ContributesAndroidInjector
    abstract fun webviewFragment(): WebviewFragment

    @ContributesAndroidInjector
    abstract fun trainingMaterialFragment(): TrainingMaterialFragment

    @ContributesAndroidInjector
    abstract fun trainingPdfFragment(): TrainingPdfFragment

    @ContributesAndroidInjector
    abstract fun trainingVideoFragment(): TrainingVideoFragment

    @ContributesAndroidInjector
    abstract fun corporateServiceFragment(): CorporateServiceCategoriesFragment

    @ContributesAndroidInjector
    abstract fun corporateServiceItemFragment(): CorporateServiceFragment

    @ContributesAndroidInjector
    abstract fun notificationFragment(): NotificationFragment

    @ContributesAndroidInjector
    abstract fun scheduleFragment(): ScheduleFragment

}