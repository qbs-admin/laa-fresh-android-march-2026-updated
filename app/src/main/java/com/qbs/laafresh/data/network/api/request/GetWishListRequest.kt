package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetWishListRequest  {

    @SerializedName("user_id")
    @Expose
    var userId : String?=null

}