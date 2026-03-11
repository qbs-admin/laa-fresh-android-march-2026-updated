package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class SubCategoriesResponse(

	@field:SerializedName(value = "sub_categories")
	val subCategories: List<SubCategoriesItem>? = null,

	@field:SerializedName(value = "success")
	val success: Int? = null,

	@field:SerializedName(value = "message")
	val message: String? = null
)

data class SubCategoriesItem(

	@field:SerializedName(value = "digital")
	val digital: Any? = null,

	@field:SerializedName(value = "sub_category_id")
	val subCategoryId: String? = null,

	@field:SerializedName(value = "sub_category_name")
	val subCategoryName: String? = null,

	@field:SerializedName(value = "description")
	val description: String? = null,

	@field:SerializedName(value = "banner")
	val banner: String? = null,

	@field:SerializedName(value = "category")
	val category: String? = null,

	@field:SerializedName(value = "brand")
	val brand: String? = null
)
