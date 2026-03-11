package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("success")
    @Expose
    var success: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("username")
    @Expose
    var userName: String? = null

    @SerializedName("surname")
    @Expose
    var surName: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("user_id")
    @Expose
    var user_id: String? = null
}