package com.envilabindonesia.android.data.repository

import com.envilabindonesia.android.api.ApiServices
import com.envilabindonesia.android.base.BasePageResponse
import com.envilabindonesia.android.base.BaseResponse
import com.envilabindonesia.android.data.request.*
import com.envilabindonesia.android.data.response.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by galihadityo on 2019-03-02.
 */

class Repository @Inject constructor(private val apiServices: ApiServices) {

    // GET

    fun getProvinces(): Single<Response<BaseResponse<BasePageResponse<ProvincesResponse>>>> {
        return apiServices.getProvinces()
    }

    fun getRegencies(idProvince: Int): Single<Response<BaseResponse<BasePageResponse<RegencyResponse>>>> {
        return apiServices.getRegencies(idProvince)
    }

    fun getDistricts(idRegency: Int): Single<Response<BaseResponse<BasePageResponse<DistrictResponse>>>> {
        return apiServices.getDistricts(idRegency)
    }

    fun getOnBoarding(): Single<Response<BaseResponse<BasePageResponse<OnboardingResponse>>>> {
        return apiServices.getOnBoarding()
    }

    fun getValidateCode(email: String, code: String): Single<Response<BaseResponse<ValidateCodeResponse>>> {
        return apiServices.getValidateCode(email, code)
    }

    fun getResendCode(email: String): Single<Response<BaseResponse<Any>>> {
        return apiServices.getResendCode(email)
    }

    fun getMemberStatusSingle(): Single<Response<BaseResponse<MemberStatusResponse>>> {
        return apiServices.getStatusMemberSingle()
    }

    fun getMemberStatus(): Observable<Response<BaseResponse<MemberStatusResponse>>> {
        return apiServices.getStatusMember()
    }

    fun getFAQ(): Single<Response<BaseResponse<List<FAQResponse>>>> {
        return apiServices.getFAQ()
    }

    fun getContactSupport(): Single<Response<BaseResponse<List<ContactSupportResponse>>>> {
        return apiServices.getContactSupport()
    }

    fun getStaticPages(slug: String): Single<Response<BaseResponse<StaticPagesResponse>>> {
        return apiServices.getStaticPages(slug)
    }

    fun getTrainingMaterial(type: String): Single<Response<BaseResponse<List<TrainingMaterialResponse>>>> {
        return apiServices.getTrainingMaterial(type)
    }

    fun getCompanies(fromRow: Int, keyword: String): Single<Response<BaseResponse<BasePageResponse<CompanyResponse>>>> {
        return apiServices.getCompanies(fromRow, keyword)
    }

    fun getServiceCategories(): Single<Response<BaseResponse<List<ServiceCategoryResponse>>>> {
        return apiServices.getServicesCategories()
    }

    fun getServiceById(fromRow: Int, categoryId: Int): Single<Response<BaseResponse<BasePageResponse<ServiceCorporateResponse>>>> {
        return apiServices.getServicesById(fromRow, categoryId)
    }

    fun getServiceByKeyword(fromRow: Int, keyword: String): Single<Response<BaseResponse<BasePageResponse<ServiceCorporateResponse>>>> {
        return apiServices.getServicesByKeyword(fromRow, keyword)
    }

    fun getContactPerson(companyId: Int): Single<Response<BaseResponse<BasePageResponse<ContactPersonResponse>>>> {
        return apiServices.getContactPerson(companyId)
    }

    fun getMyInbox(fromRow: Int): Single<Response<BaseResponse<BasePageResponse<MyInboxResponse>>>> {
        return apiServices.getMyInbox(fromRow)
    }

    fun getTransactionStatus(): Single<Response<BaseResponse<List<TransactionStatusResponse>>>> {
        return apiServices.getTransactionStatus()
    }

    fun getTransactionStatusObservable(): Observable<Response<BaseResponse<List<TransactionStatusResponse>>>> {
        return apiServices.getTransactionStatusObservable()
    }

    fun getTransactionStatusSplash(): Observable<Response<BaseResponse<List<TransactionStatusResponse>>>> {
        return apiServices.getTransactionStatusSplash()
    }

    fun getTransactionByStatusId(fromRow: Int, status: Int, isSubordinate: Boolean)
            : Single<Response<BaseResponse<BasePageResponse<TransactionResponse>>>> {
        return if (isSubordinate) apiServices.getTransactionSubordinateByStatusId(fromRow, status) else apiServices.getTransactionByStatusId(fromRow, status)
    }

    fun getTransactionByOrderId(orderId: Int)
            : Single<Response<BaseResponse<BasePageResponse<TransactionResponse>>>> {
        return apiServices.getTransactionByOrderId(orderId)
    }

    fun getTransactionByKeyword(fromRow: Int, keyword: String)
            : Single<Response<BaseResponse<BasePageResponse<TransactionResponse>>>> {
        return apiServices.getTransactionByKeyword(fromRow, keyword)
    }

    fun getSchedule(month: Int, year: Int): Single<Response<BaseResponse<List<ScheduleResponse>>>> {
        return apiServices.getSchedule(month, year)
    }

    fun getRating(orderId: Int): Single<Response<BaseResponse<RatingResponse>>> {
        return apiServices.getRating(orderId)
    }

    fun getTestResult(orderId: Int): Single<Response<BaseResponse<TestResultResponse>>> {
        return apiServices.getTestResult(orderId)
    }

    fun getDashboard(month: Int?, year: Int?): Single<Response<BaseResponse<DashboardResponse>>> {
        return apiServices.getDashboard(month, year)
    }

    fun getPerformance(): Single<Response<BaseResponse<PerformanceResponse>>> {
        return apiServices.getPerformance()
    }


    // POST

    fun postLogin(loginRequest: LoginRequest): Single<Response<BaseResponse<LoginResponse>>> {
        return apiServices.postLogin(loginRequest)
    }

    fun postForgotPassword(request: ForgotPasswordRequest): Single<Response<BaseResponse<Any>>> {
        return apiServices.postForgotPassword(request)
    }

    fun postRegister(registerRequest: RegisterRequest): Single<Response<BaseResponse<RegisterResponse>>> {
        return apiServices.postRegister(registerRequest)
    }

    fun postChangePassword(changePasswordRequest: ChangePasswordRequest)
            : Single<Response<BaseResponse<ChangePasswordResponse>>> {
        return apiServices.postChangePassword(changePasswordRequest)
    }

    fun postUpload(parts: MultipartBody.Part): Single<Response<BaseResponse<UploadResponse>>> {
        return apiServices.postUpload(parts)
    }

    fun postChangeProfile(changeProfileRequest: ChangeProfileRequest): Single<Response<BaseResponse<LoginResponse>>> {
        return apiServices.postChangeProfile(changeProfileRequest)
    }

    fun postCompanyAdd(companyAddRequest: CompanyAddRequest): Single<Response<BaseResponse<CompanyResponse>>> {
        return apiServices.postCompanyAdd(companyAddRequest)
    }

    fun postCompanyEdit(companyEditRequest: CompanyEditRequest): Single<Response<BaseResponse<CompanyResponse>>> {
        return apiServices.postCompanyEdit(companyEditRequest)
    }

    fun postContactPersonAdd(contactPersonAddRequest: ContactPersonAddRequest)
            : Single<Response<BaseResponse<ContactPersonResponse>>> {
        return apiServices.postContactPersonAdd(contactPersonAddRequest)
    }

    fun postContactPersonUpdate(contactPersonUpdateRequest: ContactPersonUpdateRequest)
            : Single<Response<BaseResponse<ContactPersonResponse>>> {
        return apiServices.postContactPersonUpdate(contactPersonUpdateRequest)
    }

    fun postRegisterDevice(registerDeviceRequest: RegisterDeviceRequest): Completable {
        return apiServices.postRegisterDevice(registerDeviceRequest)
    }

    fun postInboxRead(readInboxRequest: ReadOrDeleteInboxRequest): Completable {
        return apiServices.postInboxRead(readInboxRequest)
    }

    fun postInboxDelete(readInboxRequest: ReadOrDeleteInboxRequest): Single<Response<BaseResponse<Any>>> {
        return apiServices.postInboxDelete(readInboxRequest)
    }

    fun postOrderCreate(orderCreateRequest: OrderCreateRequest): Single<Response<BaseResponse<Any>>> {
        return apiServices.postOrderCreate(orderCreateRequest)
    }

    fun postOrderCancel(orderCancelRequest: OrderCancelRequest): Single<Response<BaseResponse<Any>>> {
        return apiServices.postOrderCancel(orderCancelRequest)
    }

    fun postOrderUpdate(request: OrderUpdateRequest): Single<Response<BaseResponse<Any>>> {
        return apiServices.postOrderUpdate(request)
    }

    fun postReview(request: ReviewRequest): Single<Response<BaseResponse<Any>>> {
        return apiServices.postReview(request)
    }

    fun postSendDocument(request: SendDocumentRequest): Single<Response<BaseResponse<SendDocumentResponse>>> {
        return apiServices.postSendDocument(request)
    }

}