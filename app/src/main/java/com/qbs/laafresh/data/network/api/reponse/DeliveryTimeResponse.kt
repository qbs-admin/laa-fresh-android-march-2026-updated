package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class DeliveryTimeResponse(

	@field:SerializedName("delivery")
	val delivery: List<String?>? = null
)
