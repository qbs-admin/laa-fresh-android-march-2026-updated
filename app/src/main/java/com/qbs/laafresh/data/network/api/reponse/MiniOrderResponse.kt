package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class MiniOrderResponse(

	@field:SerializedName("order_min_amount")
	val orderMinAmount: String? = null,

	@field:SerializedName("shipping_cost")
	val shippingCost: String? = null,

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
