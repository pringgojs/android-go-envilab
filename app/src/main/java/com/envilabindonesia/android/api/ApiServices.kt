package com.envilabindonesia.android.api

import com.envilabindonesia.android.base.BaseFragmentLoadmore
import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseResponse
import com.envilabindonesia.android.data.request.*
import com.envilabindonesia.android.data.response.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by galihadityo on 2019-03-02.
 */

interface ApiServices {

    // GET

    @GET("master/provinces")
    fun getProvinces(): Single<Response<BaseResponse<BasePageResponse<ProvincesResponse>>>>

    @GET("master/regencies")
    fun getRegencies(@Query("provinceId") idProvince: Int)
            : Single<Response<BaseResponse<BasePageResponse<RegencyResponse>>>>

    @GET("master/districts")
    fun getDistricts(@Query("regencyId") idRegency: Int)
            : Single<Response<BaseResponse<BasePageResponse<DistrictResponse>>>>

    @GET("utilities/onboarding")
    fun getOnBoarding(): Single<Response<BaseResponse<BasePageResponse<OnboardingResponse>>>>

    @GET("appsmember/status")
    fun getStatusMemberSingle(): Single<Response<BaseResponse<MemberStatusResponse>>>

    @GET("appsmember/status")
    fun getStatusMember(): Observable<Response<BaseResponse<MemberStatusResponse>>>

    @GET("member/validatecode")
    fun getValidateCode(
        @Query("email") email: String,
        @Query("code") code: String
    ): Single<Response<BaseResponse<ValidateCodeResponse>>>

    @GET("member/resendcode")
    fun getResendCode(@Query("email") email: String): Single<Response<BaseResponse<Any>>>

    @GET("staticpages/faq")
    fun getFAQ(): Single<Response<BaseResponse<List<FAQResponse>>>>

    @GET("staticpages/content")
    fun getStaticPages(@Query("slug") slug: String): Single<Response<BaseResponse<StaticPagesResponse>>>

    @GET("staticpages/trainingmaterials")
    fun getTrainingMaterial(@Query("type") slug: String)
            : Single<Response<BaseResponse<List<TrainingMaterialResponse>>>>

    @GET("authmaster/companies?showColor=1&limitRow=${BaseFragmentLoadmore.Listener.LIMIT}")
    fun getCompanies(@Query("fromRow") fromRow: Int, @Query("companyName") keyword: String)
            : Single<Response<BaseResponse<BasePageResponse<CompanyResponse>>>>

    @GET("authmaster/corporateservicecategories")
    fun getServicesCategories(): Single<Response<BaseResponse<List<ServiceCategoryResponse>>>>

    @GET("authmaster/corporateservices?limitRow=${BaseFragmentLoadmore.Listener.LIMIT}")
    fun getServicesById(@Query("fromRow") fromRow: Int, @Query("catId") categoryId: Int)
            : Single<Response<BaseResponse<BasePageResponse<ServiceCorporateResponse>>>>

    @GET("authmaster/corporateservices?limitRow=${BaseFragmentLoadmore.Listener.LIMIT}")
    fun getServicesByKeyword(@Query("fromRow") fromRow: Int, @Query("keyword") keyword: String)
            : Single<Response<BaseResponse<BasePageResponse<ServiceCorporateResponse>>>>

    @GET("authmaster/contactperson")
    fun getContactPerson(@Query("companyId") companyId: Int)
            : Single<Response<BaseResponse<BasePageResponse<ContactPersonResponse>>>>

    @GET("appsmember/myinbox?limitRow=${BaseFragmentLoadmore.Listener.LIMIT}")
    fun getMyInbox(@Query("fromRow") fromRow: Int)
            : Single<Response<BaseResponse<BasePageResponse<MyInboxResponse>>>>

    @GET("staticpages/contactsupport")
    fun getContactSupport(): Single<Response<BaseResponse<List<ContactSupportResponse>>>>

    @GET("transaction/status")
    fun getTransactionStatus(): Single<Response<BaseResponse<List<TransactionStatusResponse>>>>

    @GET("master/statusorders?background=1")
    fun getTransactionStatusSplash(): Observable<Response<BaseResponse<List<TransactionStatusResponse>>>>

    @GET("transaction/status?background=1")
    fun getTransactionStatusObservable(): Observable<Response<BaseResponse<List<TransactionStatusResponse>>>>

    @GET("transaction/search?limitRow=${BaseFragmentLoadmore.Listener.LIMIT}")
    fun getTransactionByStatusId(@Query("fromRow") fromRow: Int, @Query("status") status: Int)
            : Single<Response<BaseResponse<BasePageResponse<TransactionResponse>>>>

    @GET("transaction/search")
    fun getTransactionByOrderId(
        @Query("orderId") orderId: Int
    ): Single<Response<BaseResponse<BasePageResponse<TransactionResponse>>>>

    @GET("transaction/downline?limitRow=${BaseFragmentLoadmore.Listener.LIMIT}")
    fun getTransactionSubordinateByStatusId(@Query("fromRow") fromRow: Int, @Query("status") status: Int)
            : Single<Response<BaseResponse<BasePageResponse<TransactionResponse>>>>

    @GET("transaction/search?limitRow=${BaseFragmentLoadmore.Listener.LIMIT}")
    fun getTransactionByKeyword(@Query("fromRow") fromRow: Int, @Query("keyword") keyword: String)
            : Single<Response<BaseResponse<BasePageResponse<TransactionResponse>>>>

    @GET("transaction/schedules")
    fun getSchedule(@Query("month") month: Int, @Query("year") year: Int)
            : Single<Response<BaseResponse<List<ScheduleResponse>>>>

    @GET("transaction/testresult")
    fun getTestResult(@Query("orderId") orderId: Int): Single<Response<BaseResponse<TestResultResponse>>>

    @GET("rate/getrate")
    fun getRating(@Query("orderId") orderId: Int): Single<Response<BaseResponse<RatingResponse>>>

    @GET("dashboard/mydashboard")
    fun getDashboard(@Query("month") month: Int?, @Query("year") year: Int?)
            : Single<Response<BaseResponse<DashboardResponse>>>

    @GET("appsmember/myperformance")
    fun getPerformance(): Single<Response<BaseResponse<PerformanceResponse>>>

    // POST

    @Multipart
    @POST("upload/upfile")
    fun postUpload(@Part parts: MultipartBody.Part): Single<Response<BaseResponse<UploadResponse>>>

    @POST("member/login")
    fun postLogin(@Body loginRequest: LoginRequest): Single<Response<BaseResponse<LoginResponse>>>

    @POST("member/forgotpassword")
    fun postForgotPassword(@Body request: ForgotPasswordRequest): Single<Response<BaseResponse<Any>>>

    @POST("member/register")
    fun postRegister(@Body registerRequest: RegisterRequest): Single<Response<BaseResponse<RegisterResponse>>>

    @POST("appsmember/registerdevice")
    fun postRegisterDevice(@Body registerDeviceRequest: RegisterDeviceRequest): Completable

    @POST("appsmember/changepassword")
    fun postChangePassword(@Body changePasswordRequest: ChangePasswordRequest)
            : Single<Response<BaseResponse<ChangePasswordResponse>>>

    @POST("appsmember/changeprofile")
    fun postChangeProfile(@Body changeProfileRequest: ChangeProfileRequest)
            : Single<Response<BaseResponse<LoginResponse>>>

    @POST("authmaster/addcompany")
    fun postCompanyAdd(@Body companyAddRequest: CompanyAddRequest)
            : Single<Response<BaseResponse<CompanyResponse>>>

    @POST("authmaster/updatecompany")
    fun postCompanyEdit(@Body companyEditRequest: CompanyEditRequest)
            : Single<Response<BaseResponse<CompanyResponse>>>

    @POST("authmaster/addcontactperson")
    fun postContactPersonAdd(@Body contactPersonAddRequest: ContactPersonAddRequest)
            : Single<Response<BaseResponse<ContactPersonResponse>>>

    @POST("authmaster/updatecontactperson")
    fun postContactPersonUpdate(@Body contactPersonUpdateRequest: ContactPersonUpdateRequest)
            : Single<Response<BaseResponse<ContactPersonResponse>>>

    @POST("appsmember/readmessage")
    fun postInboxRead(@Body readInboxRequest: ReadOrDeleteInboxRequest): Completable

    @POST("appsmember/removemessage")
    fun postInboxDelete(@Body readInboxRequest: ReadOrDeleteInboxRequest)
            : Single<Response<BaseResponse<Any>>>

    @POST("transaction/create_with_notes")
    fun postOrderCreate(@Body orderCreateRequest: OrderCreateRequest)
            : Single<Response<BaseResponse<Any>>>

    @POST("transaction/cancelorder")
    fun postOrderCancel(@Body orderCancelRequest: OrderCancelRequest)
            : Single<Response<BaseResponse<Any>>>

    @POST("transaction/update_with_notes")
    fun postOrderUpdate(@Body request: OrderUpdateRequest): Single<Response<BaseResponse<Any>>>

    @POST("rate/create")
    fun postReview(@Body request: ReviewRequest): Single<Response<BaseResponse<Any>>>

    @POST("document/sending")
    fun postSendDocument(@Body request: SendDocumentRequest): Single<Response<BaseResponse<SendDocumentResponse>>>

}