package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName


data class GetAddressResponse(

	@SerializedName("address")
	val address: List<AddressItem?>? = null,

	@SerializedName("success")
	val success: String? = null,

	@SerializedName("message")
	val message: String? = null
)

data class AddressItem(

	@SerializedName("zip")
	val zip: String? = null,

	@SerializedName("country")
	val country: String? = null,

	@SerializedName("address2")
	val address2: String? = null,

	@SerializedName("city")
	val city: String? = null,

	@SerializedName("address1")
	val address1: String? = null,

	@SerializedName("created")
	val created: String? = null,

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("langlat")
	val langlat: String? = null,

	@SerializedName("user_id")
	val userId: String? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("state")
	val state: String? = null
)
