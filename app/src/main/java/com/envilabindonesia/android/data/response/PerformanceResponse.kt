package com.envilabindonesia.android.data.response
import com.google.gson.annotations.SerializedName


/**
 * Created by galihadityo on 2019-05-30.
 */

data class PerformanceResponse(
    @SerializedName("SubmittedOrders")
    val submittedOrders: SubmittedOrders?,
    @SerializedName("completedOrders")
    val completedOrders: CompletedOrders?,
    @SerializedName("monthlyOrders")
    val monthlyOrders: MonthlyOrders?,
    @SerializedName("months")
    val months: Months?,
    @SerializedName("orderChart")
    val orderChart: OrderChart?,
    @SerializedName("potentialRevenue")
    val potentialRevenue: PotentialRevenue?,
    @SerializedName("totalOrders")
    val totalOrders: TotalOrders?,
    @SerializedName("weeklyOrders")
    val weeklyOrders: WeeklyOrders?
)

data class OrderChart(
    @SerializedName("chartStats")
    val chartStats: List<ChartStat>?,
    @SerializedName("show")
    val show: Boolean?
)

data class MonthlyOrders(
    @SerializedName("chartStats")
    val chartStats: List<ChartStatX>?,
    @SerializedName("show")
    val show: Boolean?
)

data class ChartStatX(
    @SerializedName("label")
    val label: String?,
    @SerializedName("month")
    val month: String?,
    @SerializedName("monthlyTotal")
    val monthlyTotal: Int?,
    @SerializedName("year")
    val year: String?
)

data class WeeklyOrders(
    @SerializedName("chartStats")
    val chartStats: List<ChartStatXX>?,
    @SerializedName("show")
    val show: Boolean?
)

data class ChartStatXX(
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("weeklyTotal")
    val weeklyTotal: Int?,
    @SerializedName("weeks")
    val weeks: Int?
)