package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpRequest {

    @SerializedName("username")
    @Expose
    var userName : String?=null

    @SerializedName("surname")
    @Expose
    var surName : String?=null

    @SerializedName("email")
    @Expose
    var email : String?=null

    @SerializedName("password")
    @Expose
    var password : String?=null

    @SerializedName("phone")
    @Expose
    var phone : String?=null


}