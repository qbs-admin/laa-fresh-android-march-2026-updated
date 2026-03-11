package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class ProductBundleResponse(

	@field:SerializedName("success")
	val success: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("products")
	val products: List<ProductsItems>? = null
)

data class ProductsItems(

	@field:SerializedName("download_name")
	val downloadName: Any? = null,

	@field:SerializedName("vendor_featured")
	val vendorFeatured: Any? = null,

	@field:SerializedName("featured")
	val featured: String? = null,

	@field:SerializedName("deal")
	val deal: Any? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("sub_category")
	val subCategory: String? = null,

	@field:SerializedName("main_image")
	val mainImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("product_id ")
	val productId: String? = null,

	@field:SerializedName("discount")
	val discount: String? = null,

	@field:SerializedName("additional_fields")
	val additionalFields: String? = null,

	@field:SerializedName("video")
	val video: Any? = null,

	@field:SerializedName("net_weight")
	val netWeight: String? = null,

	@field:SerializedName("products")
	val products: Any? = null,

	@field:SerializedName("rating_user ")
	val ratingUser: String? = null,

	@field:SerializedName("dealers_price")
	val dealersPrice: String? = null,

	@field:SerializedName("add_timestamp")
	val addTimestamp: String? = null,

	@field:SerializedName("download")
	val download: Any? = null,

	@field:SerializedName("update_time")
	val updateTime: Any? = null,

	@field:SerializedName("rating_num ")
	val ratingNum: String? = null,

	@field:SerializedName("last_viewed")
	val lastViewed: String? = null,

	@field:SerializedName("options")
	val options: String? = null,

	@field:SerializedName("logo")
	val logo: Any? = null,

	@field:SerializedName("tag")
	val tag: String? = null,

	@field:SerializedName("brand")
	val brand: Any? = null,

	@field:SerializedName("rating_total ")
	val ratingTotal: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("number_of_view")
	val numberOfView: String? = null,

	@field:SerializedName("requirements")
	val requirements: Any? = null,

	@field:SerializedName("shipping_cost")
	val shippingCost: String? = null,

	@field:SerializedName("num_of_imgs")
	val numOfImgs: String? = null,

	@field:SerializedName("tax")
	val tax: String? = null,

	@field:SerializedName("discount_type")
	val discountType: String? = null,

	@field:SerializedName("sale_price")
	val salePrice: String? = null,

	@field:SerializedName("front_image")
	val frontImage: String? = null,

	@field:SerializedName("added_by ")
	val addedBy: String? = null,

	@field:SerializedName("is_bundle")
	val isBundle: String? = null,

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("current_stock")
	val currentStock: String? = null,

	@field:SerializedName("num_of_downloads")
	val numOfDownloads: String? = null,

	@field:SerializedName("tax_type")
	val taxType: String? = null,

	@field:SerializedName("background")
	val background: Any? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("purchase_price")
	val purchasePrice: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("product_deal_days")
	val productDealDays: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
