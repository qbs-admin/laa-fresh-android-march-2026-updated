package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetCouponResponse(

    @SerializedName("coupondetails")
    @Expose
    val coupondetails: ArrayList<CouponDetail>? = null,

    @SerializedName("success")
    val success: String? = null,

    @SerializedName("message")
    val message: String? = null
)

class CouponDetail {
    @SerializedName("coupon_id")
    @Expose
    val couponId: String? = null

    @SerializedName("title")
    @Expose
    val title: String? = null

    @SerializedName("spec")
    @Expose
    val spec: CouponSpec? = null

    @SerializedName("added_by")
    @Expose
    val addedBy: AddedBy? = null

    @SerializedName("till")
    @Expose
    val till: String? = null

    @SerializedName("code")
    @Expose
    val code: String? = null

    @SerializedName("status")
    @Expose
    val status: String? = null
}

class CouponSpec {
    @SerializedName("set_type")
    @Expose
    val setType: String? = null

    @SerializedName("set")
    @Expose
    val set: List<String>? = null

    @SerializedName("discount_type")
    @Expose
    val discountType: String? = null

    @SerializedName("discount_value")
    @Expose
    val discountValue: String? = null

    @SerializedName("shipping_free")
    @Expose
    val shippingFree: String? = null
}


class AddedBy {
    @SerializedName("type")
    @Expose
    private val type: String? = null

    @SerializedName("id")
    @Expose
    private val id: String? = null
}
