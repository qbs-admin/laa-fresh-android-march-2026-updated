package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAddressDetailsRequest  {

    @SerializedName("id")
    @Expose
    var id : String?=null

}