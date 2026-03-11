package com.qbs.laafresh.data.dbhelper.table

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["productId"], unique = true)])
class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    var sno: Int = 0
    var productId: String = ""
    var ratingNum: String = ""
    var ratingTotal: String = ""
    var title: String = ""
    var category: String = ""
    var description: String = ""
    var subCategory: String = ""
    var salePrice: String = ""
    var purchasePrice: String = ""
    var currentStock: String = ""
    var discount: String = ""
    var discountType: String = ""
    var tax: String = ""
    var taxType: String = ""
    var logo: String = ""
    var image: String = ""
    var featured: String = ""
    var deal: String = ""
    var net_weight:String = ""
    var shipping_cost: String = ""

    constructor(productId: String, ratingNum: String, ratingTotal: String, title: String,
                category: String, description: String, subCategory: String, salePrice: String,
                purchasePrice: String, currentStock: String, discount: String, discountType: String,
                tax: String, taxType: String, logo: String, image: String, deal: String,featured:String,net_weight:String,shipping_cost:String) {
        this.productId = productId
        this.ratingNum = ratingNum
        this.ratingTotal = ratingTotal
        this.title = title
        this.category = category
        this.description = description
        this.subCategory = subCategory
        this.salePrice = salePrice
        this.purchasePrice = purchasePrice
        this.currentStock = currentStock
        this.discount = discount
        this.discountType = discountType
        this.tax = tax
        this.taxType = taxType
        this.logo = logo
        this.image = image
        this.deal = deal
        this.featured = featured
        this.net_weight = net_weight
        this.shipping_cost = shipping_cost
    }
}