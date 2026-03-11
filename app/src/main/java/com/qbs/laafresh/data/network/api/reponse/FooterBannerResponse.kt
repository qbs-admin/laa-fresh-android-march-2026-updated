package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class FooterBannerResponse(

	@SerializedName("footer_image")
	val footerImage: List<FooterImageItem?>? = null
)

data class FooterImageItem(

	@SerializedName("images")
	val images: String? = null
)
