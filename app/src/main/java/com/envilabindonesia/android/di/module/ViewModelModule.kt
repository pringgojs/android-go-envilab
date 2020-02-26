package com.envilabindonesia.android.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.envilabindonesia.android.di.ViewModelFactory
import com.envilabindonesia.android.di.ViewModelKey
import com.envilabindonesia.android.ui.account.DistrictsViewModel
import com.envilabindonesia.android.ui.account.ProvincesViewModel
import com.envilabindonesia.android.ui.account.RegenciesViewModel
import com.envilabindonesia.android.ui.account.password.PasswordViewModel
import com.envilabindonesia.android.ui.account.profile.ProfileViewModel
import com.envilabindonesia.android.ui.account.upload.UploadViewModel
import com.envilabindonesia.android.ui.client.add.ClientAddViewModel
import com.envilabindonesia.android.ui.client.contact.ContactPersonViewModel
import com.envilabindonesia.android.ui.client.contact.list.ContactPersonListViewModel
import com.envilabindonesia.android.ui.client.edit.ClientEditViewModel
import com.envilabindonesia.android.ui.client.search.SearchClientViewModel
import com.envilabindonesia.android.ui.contact.ContactViewModel
import com.envilabindonesia.android.ui.dashboard.DashboardViewModel
import com.envilabindonesia.android.ui.faq.FAQViewModel
import com.envilabindonesia.android.ui.forgot.ForgotPasswordViewModel
import com.envilabindonesia.android.ui.home.HomeViewModel
import com.envilabindonesia.android.ui.login.LoginViewModel
import com.envilabindonesia.android.ui.notification.NotificationViewModel
import com.envilabindonesia.android.ui.notification.detail.NotificationDetailViewModel
import com.envilabindonesia.android.ui.order.OrderCancelViewModel
import com.envilabindonesia.android.ui.order.OrderUpdateViewModel
import com.envilabindonesia.android.ui.order.add.MemberStatusViewModel
import com.envilabindonesia.android.ui.order.add.OrderAddViewModel
import com.envilabindonesia.android.ui.order.detail.OrderDetailViewModel
import com.envilabindonesia.android.ui.order.detail.QuotationInvoiceViewModel
import com.envilabindonesia.android.ui.order.detail.SendDocumentViewModel
import com.envilabindonesia.android.ui.order.list.OrderListViewModel
import com.envilabindonesia.android.ui.order.list.item.OrderListPerItemViewModel
import com.envilabindonesia.android.ui.order.search.OrderSearchViewModel
import com.envilabindonesia.android.ui.performance.PerformanceViewModel
import com.envilabindonesia.android.ui.register.RegisterViewModel
import com.envilabindonesia.android.ui.review.ReviewAddViewModel
import com.envilabindonesia.android.ui.review.ReviewListViewModel
import com.envilabindonesia.android.ui.schedule.ScheduleViewModel
import com.envilabindonesia.android.ui.search.SearchViewModel
import com.envilabindonesia.android.ui.services.CorporateServiceViewModel
import com.envilabindonesia.android.ui.services.category.CorporateServiceCategoriesViewModel
import com.envilabindonesia.android.ui.services.search.SearchServiceViewModel
import com.envilabindonesia.android.ui.splash.SplashViewModel
import com.envilabindonesia.android.ui.training.TrainingMaterialViewModel
import com.envilabindonesia.android.ui.verification.VerificationViewModel
import com.envilabindonesia.android.ui.webview.WebviewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by galihadityo on 2019-03-02.
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(QuotationInvoiceViewModel::class)
    abstract fun bindQuotationInvoiceViewModel(quotationInvoiceViewModel: QuotationInvoiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SendDocumentViewModel::class)
    abstract fun bindSendDocumentViewModel(sendDocumentViewModel: SendDocumentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PerformanceViewModel::class)
    abstract fun bindPerformanceViewModel(performanceViewModel: PerformanceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MemberStatusViewModel::class)
    abstract fun bindMemberStatusViewModel(memberStatusViewModel: MemberStatusViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(performanceViewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewAddViewModel::class)
    abstract fun bindReviewAddViewModel(reviewAddViewModel: ReviewAddViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewListViewModel::class)
    abstract fun bindReviewListViewModel(reviewListViewModel: ReviewListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderUpdateViewModel::class)
    abstract fun bindOrderUpdateViewModel(orderUpdateViewModel: OrderUpdateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderCancelViewModel::class)
    abstract fun bindOrderCancelViewModel(orderCancelViewModel: OrderCancelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleViewModel(scheduleViewModel: ScheduleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderDetailViewModel::class)
    abstract fun bindOrderDetailViewModel(orderDetailViewModel: OrderDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderSearchViewModel::class)
    abstract fun bindOrderSearchViewModel(orderSearchViewModel: OrderSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderListPerItemViewModel::class)
    abstract fun bindOrderListPerItemViewModel(orderListPerItemViewModel: OrderListPerItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderListViewModel::class)
    abstract fun bindOrderListViewModel(orderListViewModel: OrderListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderAddViewModel::class)
    abstract fun bindOrderAddViewModel(orderAddViewModel: OrderAddViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchServiceViewModel::class)
    abstract fun bindSearchServiceViewModel(searchServiceViewModel: SearchServiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel::class)
    abstract fun bindContactViewModel(contactViewModel: ContactViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationDetailViewModel::class)
    abstract fun bindNotificationDetailViewModel(notificationDetailViewModel: NotificationDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindNotificationViewModel(notificationViewModel: NotificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactPersonViewModel::class)
    abstract fun bindContactPersonViewModel(contactPersonViewModel: ContactPersonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactPersonListViewModel::class)
    abstract fun bindContactPersonListViewModel(contactPersonListViewModel: ContactPersonListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ClientEditViewModel::class)
    abstract fun bindClientEditViewModel(clientEditViewModel: ClientEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ClientAddViewModel::class)
    abstract fun bindClientAddViewModel(clientAddViewModel: ClientAddViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CorporateServiceViewModel::class)
    abstract fun bindCorporateServiceItemViewModel(corporateServiceItemViewModel: CorporateServiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CorporateServiceCategoriesViewModel::class)
    abstract fun bindCorporateServiceViewModel(corporateServiceViewModel: CorporateServiceCategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchClientViewModel::class)
    abstract fun bindSearchClientViewModel(searchClientViewModel: SearchClientViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DistrictsViewModel::class)
    abstract fun bindDistrictsViewModel(districtsViewModel: DistrictsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegenciesViewModel::class)
    abstract fun bindRegenciesViewModel(regenciesViewModel: RegenciesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProvincesViewModel::class)
    abstract fun bindProvincesViewModel(provincesViewModel: ProvincesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingMaterialViewModel::class)
    abstract fun bindTrainingMaterialViewModel(trainingMaterialViewModel: TrainingMaterialViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WebviewViewModel::class)
    abstract fun bindWebviewViewModel(webviewViewModel: WebviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UploadViewModel::class)
    abstract fun bindUploadViewModel(uploadViewModel: UploadViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FAQViewModel::class)
    abstract fun bindFAQViewModel(fAQViewModel: FAQViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordViewModel::class)
    abstract fun bindPasswordViewModel(passwordViewModel: PasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel::class)
    abstract fun bindForgotPasswordViewModel(forgotPasswordViewModel: ForgotPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VerificationViewModel::class)
    abstract fun bindVerificationViewModel(verificationViewModel: VerificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}