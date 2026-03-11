package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductDetailRequest {

    @SerializedName("subcategory_id")
    @Expose
    var product_id : String?=null

}