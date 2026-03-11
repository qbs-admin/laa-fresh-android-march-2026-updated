package com.qbs.laafresh.data.dbhelper.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull
import androidx.room.Ignore

@Entity
data class ProductPriceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "serialNumber")
    var sno: Int = 0,

    @ColumnInfo(name = "id")
    var id: String? = null,

    @ColumnInfo(name = "product_id")
    var productId: String? = null,

    @ColumnInfo(name = "unit")
    var unit: String? = null,

    @ColumnInfo(name = "sale_price")
    var salePrice: String? = null,

    @ColumnInfo(name = "purchase_price")
    var purchasePrice: String? = null,

    @ColumnInfo(name = "shipping_cost")
    var shippingCost: String? = null,

    @ColumnInfo(name = "discount")
    var discount: String? = null,

    @ColumnInfo(name = "discount_type")
    var discountType: String? = null,

    @ColumnInfo(name = "tax")
    var tax: String? = null,

    @ColumnInfo(name = "tax_type")
    var taxType: String? = null
)