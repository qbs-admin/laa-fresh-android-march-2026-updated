package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForgotPasswordRequest {
    @SerializedName("email")
    @Expose
    var email : String?=null
}