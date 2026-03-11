package com.qbs.laafresh.data.dbhelper.table

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["productCode"], unique = true)])
class MyCartEntity {

    @PrimaryKey(autoGenerate = true)
    var sno: Int = 0
    var productCode: String = ""
    var subCategoryCode: String = ""
    var categoryCode: String = ""
    var productName: String = ""
    var orderQty: Int = 0
    var orderValue: Double = 0.0
    var productWight: String = ""
    var productURI: String = ""
    var priceId: String = ""
    var user_id: String = ""
    var subtotal: Double = 0.0
    var discountAmount: Double = 0.0
    var tax: Double = 0.0
    var defaultTax: Double = 0.0
    var defaultDiscountAmount: Double = 0.0
    var shipping_cost: Double = 0.0
    var defaultShippingCost: Double = 0.0
    var defaultProductPrice: Double = 0.0

    constructor(
        productCode: String,
        subCategoryCode: String,
        categoryCode: String,
        productName: String,
        orderQty: Int,
        orderValue: Double,
        productWight: String,
        productURI: String,
        priceId: String,
        user_id: String,
        discountAmount: Double,
        subtotal: Double,
        tax: Double,
        shipping_cost: Double,
        defaultShippingCost: Double,
        defaultTax: Double,
        defaultDiscountAmount: Double,
        defaultProductPrice: Double
    ) {
        this.productCode = productCode
        this.subCategoryCode = subCategoryCode
        this.categoryCode = categoryCode
        this.productName = productName
        this.orderQty = orderQty
        this.orderValue = orderValue
        this.productWight = productWight
        this.productURI = productURI
        this.priceId = priceId
        this.user_id = user_id
        this.discountAmount = discountAmount
        this.subtotal = subtotal
        this.tax = tax
        this.shipping_cost = shipping_cost
        this.defaultShippingCost = defaultShippingCost
        this.defaultTax = defaultTax
        this.defaultDiscountAmount = defaultDiscountAmount
        this.defaultProductPrice = defaultProductPrice
    }
}