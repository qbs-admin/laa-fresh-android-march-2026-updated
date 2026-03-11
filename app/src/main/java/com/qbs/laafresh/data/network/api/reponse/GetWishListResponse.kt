package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GetWishListResponse {

    @SerializedName("products")
    @Expose
    val products: List<WishListProductItem>? = null
    @SerializedName("success")
    @Expose
    val success: Int? = null
    @SerializedName("message")
    @Expose
    val message: String = ""
}