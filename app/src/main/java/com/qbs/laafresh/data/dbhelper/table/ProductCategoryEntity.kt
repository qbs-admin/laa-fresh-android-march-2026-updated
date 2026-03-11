package com.qbs.laafresh.data.dbhelper.table

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class ProductCategoryEntity {
    @PrimaryKey(autoGenerate = true)
    var sno: Int = 0
    var categoryId: String = ""
    var categoryName: String = ""

    constructor(categoryId: String, categoryName: String) {
        this.categoryId = categoryId
        this.categoryName = categoryName
    }
}