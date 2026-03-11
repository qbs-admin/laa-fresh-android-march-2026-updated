package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddWishListRequest {

    @SerializedName("user_id")
    @Expose
    var userId : String?=null

    @SerializedName("product_id")
    @Expose
    var productId : String?=null
}