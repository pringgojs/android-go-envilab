package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-05-26.
 */

data class DashboardResponse(
    @SerializedName("months")
    val months: Months?,
    @SerializedName("myChildOrders")
    val myChildOrders: MyChildOrders?,
    @SerializedName("myOrders")
    val myOrders: MyOrders?,
    @SerializedName("myOrdersChart")
    val myOrdersChart: MyOrdersChart?,
    @SerializedName("myPerformance")
    val myPerformance: MyPerformance?
)

data class MyChildOrders(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("preview")
    val preview: List<Preview>?,
    @SerializedName("show")
    val show: Boolean?,
    @SerializedName("totalAmount")
    val totalAmount: Int?
)

data class MyOrders(
    @SerializedName("preview")
    val preview: List<Preview>?,
    @SerializedName("show")
    val show: Boolean?
)

data class Preview(
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
    @SerializedName("status")
    val status: Status?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("updated")
    val updated: String?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("userRegisterAs")
    val userRegisterAs: Int?,
    @SerializedName("user")
    val user: User?
)

data class Status(
    @SerializedName("date")
    val date: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("latest")
    val latest: Int?
)

data class MyOrdersChart(
    @SerializedName("chartStats")
    val chartStats: List<ChartStat>?,
    @SerializedName("show")
    val show: Boolean?
)

data class ChartStat(
    @SerializedName("ordersFound")
    val ordersFound: Int?,
    @SerializedName("ordersFoundPercentage")
    val ordersFoundPercentage: Double?,
    @SerializedName("statusId")
    val statusId: Int?,
    @SerializedName("statusName")
    val statusName: String?
)

data class MyPerformance(
    @SerializedName("SubmittedOrders")
    val submittedOrders: SubmittedOrders?,
    @SerializedName("completedOrders")
    val completedOrders: CompletedOrders?,
    @SerializedName("potentialRevenue")
    val potentialRevenue: PotentialRevenue?,
    @SerializedName("totalOrders")
    val totalOrders: TotalOrders?
)

data class TotalOrders(
    @SerializedName("show")
    val show: Boolean?,
    @SerializedName("totalAmount")
    val totalAmount: Int?
)

data class PotentialRevenue(
    @SerializedName("show")
    val show: Boolean?,
    @SerializedName("tearingCommissionLabel")
    val tearingCommissionLabel: String?,
    @SerializedName("totalAmount")
    val totalAmount: Int?
)

data class SubmittedOrders(
    @SerializedName("orders")
    val orders: Int?,
    @SerializedName("show")
    val show: Boolean?
)

data class CompletedOrders(
    @SerializedName("orders")
    val orders: Int?,
    @SerializedName("show")
    val show: Boolean?
)

data class Months(
    @SerializedName("optionMonths")
    val optionMonths: List<OptionMonth>?,
    @SerializedName("requestedMonth")
    val requestedMonth: RequestedMonth?
)

data class OptionMonth(
    @SerializedName("label")
    val label: String?,
    @SerializedName("month")
    val month: String?,
    @SerializedName("year")
    val year: String?
)

data class RequestedMonth(
    @SerializedName("label")
    val label: String?,
    @SerializedName("month")
    val month: String?,
    @SerializedName("year")
    val year: String?
)