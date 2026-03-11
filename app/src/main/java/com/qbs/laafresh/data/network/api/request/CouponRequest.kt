package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CouponRequest {

    @SerializedName("couponcode")
    @Expose
    var couponcode: String? = null

    @SerializedName("user_id")
    @Expose
    var userId: String? = null

}