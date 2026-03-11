package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.SerializedName

data class InstaMojoRequest(

	@SerializedName("firstname")
	var firstname: String? = null,

	@SerializedName("phone")
	var phone: String? = null,

	@SerializedName("grand_total")
	var grandTotal: String? = null,

	@SerializedName("order_id")
	var orderId: String? = null,

	@SerializedName("email")
	var email: String? = null
)
