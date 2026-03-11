package com.qbs.laafresh.data.dbhelper.table
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["subCategoryId"], unique = true)])
class SubCategoriesItemEntity {
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0
    var digital: String=""
    var subCategoryId: String=""
    var subCategoryName: String=""
    var description: String=""
    var banner: String=""
    var category: String=""
    var brand: String=""

    constructor(
        digital: String,
        subCategoryId: String,
        subCategoryName: String,
        description: String,
        banner: String,
        category: String,
        brand: String
    ) {
        this.Id = Id
        this.digital = digital
        this.subCategoryId = subCategoryId
        this.subCategoryName = subCategoryName
        this.description = description
        this.banner = banner
        this.category = category
        this.brand = brand
    }
}