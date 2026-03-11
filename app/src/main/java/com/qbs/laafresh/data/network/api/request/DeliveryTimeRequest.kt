package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.SerializedName

data class DeliveryTimeRequest(

	@SerializedName("date")
	var date: String? = null
)
