package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IndividualProductRequest {
    @SerializedName("product_id")
    @Expose
    var product_id : String?=null
    @SerializedName("userid")
    @Expose
    var userid : String?=null

}