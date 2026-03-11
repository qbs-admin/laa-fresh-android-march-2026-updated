package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.SerializedName

data class AddAddressRequest(

	@SerializedName("zip")
	var zip: String? = null,

	@SerializedName("country")
	var country: String? = null,

	@SerializedName("langlat")
	var langlat: String? = null,

	@SerializedName("user_id")
	var userId: String? = null,

	@SerializedName("address2")
	var address2: String? = null,

	@SerializedName("phone")
	var phone: String? = null,

	@SerializedName("city")
	var city: String? = null,

	@SerializedName("address1")
	var address1: String? = null,

	@SerializedName("name")
	var name: String? = null,

	@SerializedName("state")
	var state: String? = null,

	@SerializedName("type")
	var type: String? = null,

	@SerializedName("default_address")
	var defaultAddress: String? = null
)
