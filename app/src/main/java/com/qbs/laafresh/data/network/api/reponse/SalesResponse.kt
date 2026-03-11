package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class SalesResponse(

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("couponmessage")
    val couponmessage: String? = null,

    @SerializedName("sale_id")
    val saleId: Int? = null,

    @SerializedName("longurl")
    val longurl: String? = null,

    @SerializedName("redirect_url")
    val redirect_url: String?  = null,

    @SerializedName("grand_total")
    val grand_total: Double? = null,

    @SerializedName("payment_type")
    val payment_type: String? = null,

    @SerializedName("success")
    val success: String? = null,

    @SerializedName("message")
    val message: String? = null
)