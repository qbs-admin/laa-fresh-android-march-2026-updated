package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpResponse {

    @SerializedName("success")
    @Expose
    var success : String?=null

    @SerializedName("message")
    @Expose
    var message : String?=null
}