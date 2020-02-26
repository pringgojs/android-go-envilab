package com.envilabindonesia.android.data.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by galihadityo on 2019-05-11.
 */

@Parcelize
data class TransactionResponse(
    @SerializedName("owner")
    val isOwner: Boolean?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("companyAddress")
    val companyAddress: String?,
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("companyName")
    val companyName: String?,
    @SerializedName("cpEmail")
    val cpEmail: String?,
    @SerializedName("cpId")
    val cpId: Int?,
    @SerializedName("cpName")
    val cpName: String?,
    @SerializedName("cpPhone")
    val cpPhone: String?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("districtId")
    val districtId: Int?,
    @SerializedName("districtName")
    val districtName: String?,
    @SerializedName("orderId")
    val orderId: Int?,
    @SerializedName("provinceId")
    val provinceId: Int?,
    @SerializedName("provinceName")
    val provinceName: String?,
    @SerializedName("regencyId")
    val regencyId: Int?,
    @SerializedName("regencyName")
    val regencyName: String?,
    @SerializedName("services")
    val services: List<ServiceCorporateResponse>?,
    @SerializedName("progress")
    val progress: List<TransactionProgress>?,
    @SerializedName("status")
    val status: TransactionStatus?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("updated")
    val updated: String?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("userRegisterAs")
    val userRegisterAs: Int?,
    @SerializedName("quotation")
    val quotation: Quotation?,
    @SerializedName("quotationData")
    val quotationData: QuotationData?,
    @SerializedName("invoice")
    val invoice: Invoice?,
    @SerializedName("invoiceData")
    val invoiceData: InvoiceData?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("notes")
    val notes: String?
): Parcelable

@Parcelize
data class User(
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone")
    val phone: String?
): Parcelable

@Parcelize
data class InvoiceData(
    @SerializedName("number")
    val number: String? = "",
    @SerializedName("defaultEmail")
    val defaultEmail: String? = "",
    @SerializedName("pdf")
    val pdf: String? = ""
): Parcelable

@Parcelize
data class Invoice(
    @SerializedName("show")
    val isShow: Boolean?
): Parcelable

@Parcelize
data class QuotationData(
    @SerializedName("number")
    val number: String? = "",
    @SerializedName("defaultEmail")
    val defaultEmail: String? = "",
    @SerializedName("pdf")
    val pdf: String? = ""
): Parcelable

@Parcelize
data class Quotation(
    @SerializedName("show")
    val isShow: Boolean?
): Parcelable

@Parcelize
data class TransactionStatus(
    @SerializedName("date")
    val date: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("latest")
    val latest: Int?
): Parcelable

@Parcelize
data class TransactionProgress(
    @SerializedName("date")
    val date: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("status")
    val status: Int?
): Parcelable