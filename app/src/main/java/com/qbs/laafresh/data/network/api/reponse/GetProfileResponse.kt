package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@SerializedName("user_id")
	val userId: String? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("success")
	val success: String? = null,

	@SerializedName("surname")
	val surname: String? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("username")
	val username: String? = null
)
