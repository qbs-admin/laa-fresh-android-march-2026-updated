package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(

	@SerializedName("skype")
	val skype: String? = null,

	@SerializedName("user_id")
	val userId: String? = null,

	@SerializedName("address2")
	val address2: String? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("surname")
	val surname: String? = null,

	@SerializedName("facebook")
	val facebook: String? = null,

	@SerializedName("google_plus")
	val googlePlus: String? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("username")
	val username: String? = null
)
