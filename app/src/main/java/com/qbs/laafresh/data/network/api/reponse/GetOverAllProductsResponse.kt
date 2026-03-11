package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetOverAllProductsResponse(

	@SerializedName("success")
	@Expose
	val success: Int? = null,

	@SerializedName("message")
	@Expose
	val message: String? = null,

	@SerializedName("products")
	@Expose
	val products: List<GetAllProductsItem>? = null
)

data class GetAllProductsItem(

	@SerializedName("image")
	@Expose
	val image: String? = null,

	@SerializedName("rating_num")
	@Expose
	val ratingNum: String? = null,

	@SerializedName("deal")
	@Expose
	val deal: String? = null,

	@SerializedName("images")
	@Expose
	val images: List<String?>? = null,

	@SerializedName("sub_category")
	@Expose
	val subCategory: String? = null,

	@SerializedName("rating_user")
	@Expose
	val ratingUser: String? = null,

	@SerializedName("description")
	@Expose
	val description: String? = null,

	@SerializedName("num_of_imgs")
	@Expose
	val numOfImgs: String? = null,

	@SerializedName("discount")
	@Expose
	val discount: String? = null,

	@SerializedName("tax")
	@Expose
	val tax: String? = null,

	@SerializedName("title")
	@Expose
	val title: String? = null,

	@SerializedName("discount_type")
	@Expose
	val discountType: String? = null,

	@SerializedName("sale_price")
	@Expose
	val salePrice: String? = null,

	@SerializedName("rating_total")
	@Expose
	val ratingTotal: String? = null,

	@SerializedName("is_bundle")
	@Expose
	val isBundle: String? = null,

	@SerializedName("current_stock")
	@Expose
	val currentStock: String? = null,

	@SerializedName("tax_type")
	@Expose
	val taxType: String? = null,

	@SerializedName("product_id")
	@Expose
	val productId: String? = null,

	@SerializedName("purchase_price")
	@Expose
	val purchasePrice: String? = null,

	@SerializedName("logo")
	@Expose
	val logo: Any? = null,

	@SerializedName("category")
	@Expose
	val category: String? = null
)
