package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class WishListProductItem {

    @SerializedName("product_id")
    @Expose
    val productId: String = ""
    @SerializedName("title")
    @Expose
    val title: String = ""
    @SerializedName("image")
    @Expose
    val image: String = ""
}