package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductItems {

    @SerializedName("product_id")
    @Expose
    val productId: String = ""
    @SerializedName("rating_num")
    @Expose
    val ratingNum: String = ""
    @SerializedName("rating_total")
    @Expose
    val ratingTotal: String = ""
    @SerializedName("title")
    @Expose
    val title: String = ""
    @SerializedName("category")
    @Expose
    val category: String = ""

    @SerializedName("image")
    @Expose
    val image: String = ""

    @SerializedName("description")
    @Expose
    val description: String = ""
    @SerializedName("sub_category")
    @Expose
    val subCategory: String = ""
    @SerializedName("sale_price")
    @Expose
    val salePrice: String = ""
    @SerializedName("purchase_price")
    @Expose
    val purchasePrice: String = ""
    @SerializedName("shipping_cost")
    @Expose
    val shippingCost: String = ""

    @SerializedName("current_stock")
    @Expose
    val currentStock: String = ""

    @SerializedName("discount")
    @Expose
    val discount: String = ""
    @SerializedName("discount_type")
    @Expose
    val discountType: String = ""
    @SerializedName("tax")
    @Expose
    val tax: String = ""
    @SerializedName("tax_type")
    @Expose
    val taxType: String = ""
    @SerializedName("main_image")
    @Expose
    val mainImage: String = ""
    @SerializedName("deal")
    @Expose
    val deal: String = ""
    @SerializedName("logo")
    @Expose
    val logo: String = ""

    @SerializedName("product_deal_days")
    @Expose
    val product_deal_days: String = ""

}