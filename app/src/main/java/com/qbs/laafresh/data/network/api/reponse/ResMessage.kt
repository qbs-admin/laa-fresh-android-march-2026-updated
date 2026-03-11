package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class ResMessage(

    @field:SerializedName(value = "message"/*,alternate = ["error_description"]*/)
    val message: String? = null,

    @field:SerializedName(value = "error_code"/*,alternate = ["error_description"]*/)
    val error_code: Int? = null,

    @field:SerializedName(value = "order_id"/*,alternate = ["error_description"]*/)
    val order_id: Int? = null


)