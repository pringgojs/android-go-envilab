package com.envilabindonesia.android.data.request
import com.google.gson.annotations.SerializedName

/**
 * Created by galihadityo on 2019-06-03.
 */

data class SendDocumentRequest(
    @SerializedName("orderId")
    val orderId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("type")
    val type: String
)

enum class DocumentType(val value: String) {
    INVOICE("invoice"),
    QUOTATION("quotation")
}