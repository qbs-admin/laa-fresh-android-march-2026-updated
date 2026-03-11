package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PincodeRequest  {

    @SerializedName("zipcode")
    @Expose
    var zipcode : String?=null

}