package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName


data class GetProductListResponse(

    @SerializedName("success")
    val success: Int? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("products")
    val products: List<ProductsItem>? = null
)

data class ProductsItem(

    @SerializedName("rating_num")
    val rating_num: String? = null,

    @SerializedName("rating_total")
    val rating_total: String? = null,

    @SerializedName("rating_user")
    val rating_user: String? = null,

    @SerializedName("category")
    val category: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("sub_category")
    val sub_category: String? = null,

    @SerializedName("purchase_price")
    val purchase_price: String? = null,

    @SerializedName("discount")
    val discount: String? = null,

    @SerializedName("discount_type")
    val discount_type: String? = null,

    @SerializedName("tax")
    val tax: String? = null,

    @SerializedName("tax_type")
    val tax_type: String? = null,

    @SerializedName("logo")
    val logo: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("featured")
    val featured: String? = null,

    @SerializedName("deal")
    val deal: String? = null,

    @SerializedName("current_stock")
    val currentStock: String? = null,

    /*@SerializedName("main_image")
    val mainImage: String? = null,*/

    @SerializedName("product_id")
    val productId: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("sale_price")
    val salePrice: String? = null,

    @SerializedName("main_image")
    val main_image: String? = null,

    @SerializedName("net_weight")
    val net_weight: String? = null,

    @SerializedName("shipping_cost")
    val shipping_cost: String? = null
)
