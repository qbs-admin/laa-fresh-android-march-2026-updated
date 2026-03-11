package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class PincodeResponse(

	@SerializedName("zipcode")
	val zipcode: Any? = null,

	@SerializedName("success")
	val success: String? = null,

	@SerializedName("message")
	val message: String? = null
)
