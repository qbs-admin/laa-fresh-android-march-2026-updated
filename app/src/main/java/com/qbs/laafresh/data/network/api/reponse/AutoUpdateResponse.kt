package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class AutoUpdateResponse(

	@field:SerializedName("app_version")
	val appVersion: String? = null
)
