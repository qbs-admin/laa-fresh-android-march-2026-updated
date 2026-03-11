package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName


data class OfferResponse(

	@SerializedName("success")
	val success: Int? = null,

	@SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@SerializedName("message")
	val message: String? = null
)

data class OfferProductsItem(

	@SerializedName("image")
	val image: String? = null,

	@SerializedName("rating_num")
	val ratingNum: String? = null,

	@SerializedName("deal")
	val deal: String? = null,

	@SerializedName("sub_category")
	val subCategory: Any? = null,

	@SerializedName("rating_user")
	val ratingUser: String? = null,

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("num_of_imgs")
	val numOfImgs: String? = null,

	@SerializedName("discount")
	val discount: String? = null,

	@SerializedName("tax")
	val tax: String? = null,

	@SerializedName("title")
	val title: String? = null,

	@SerializedName("discount_type")
	val discountType: String? = null,

	@SerializedName("sale_price")
	val salePrice: String? = null,

	@SerializedName("rating_total")
	val ratingTotal: String? = null,

	@SerializedName("is_bundle")
	val isBundle: String? = null,

	@SerializedName("current_stock")
	val currentStock: String? = null,

	@SerializedName("tax_type")
	val taxType: String? = null,

	@SerializedName("product_id")
	val productId: String? = null,

	@SerializedName("purchase_price")
	val purchasePrice: String? = null,

	@SerializedName("logo")
	val logo: Any? = null,

	@SerializedName("category")
	val category: Any? = null
)

data class CategoriesItem(

	@SerializedName("category_id")
	val categoryId: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("products")
	val products: List<OfferProductsItem?>? = null
)
