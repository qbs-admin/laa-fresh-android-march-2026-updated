package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class AddAddressResponse(

    @SerializedName("success")
    val success: String? = null,

    @SerializedName("message")
    val message: String? = null
)

