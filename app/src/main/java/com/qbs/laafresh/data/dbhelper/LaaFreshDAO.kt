package com.qbs.laafresh.data.dbhelper

import androidx.room.*
import com.qbs.laafresh.data.dbhelper.table.*


@Dao
interface LaaFreshDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubCategoriesItem(subCategoriesItem: List<SubCategoriesItemEntity>)

    @Query("select * from SubCategoriesItemEntity")
    fun getSubCategoriesItem(): List<SubCategoriesItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductItems(productItems: List<ProductEntity>)

//    @Query("DELETE FROM MyCartEntity")
//    fun clearProducts()

    @Query("select * from ProductEntity where ProductEntity.subCategory=:categoryId")
    fun getProductItems(categoryId: String): List<ProductEntity>

    @Query("select * from ProductEntity where ProductEntity.productId=:productId")
    fun getProductItemsDetails(productId: String): List<ProductEntity>

    @Insert
    fun insertUserLogin(loginEntity: LoginEntity)

    @Query("select * from LoginEntity")
    fun getUserDetail(): LoginEntity

    @Query("delete from LoginEntity")
    fun deleteUserDetail()


    @Query("delete from ProductEntity")
    fun deleteAllProduct()

    @Insert
    fun insertProductCategory(categoryList: List<ProductCategoryEntity>)

    @Query("delete from ProductCategoryEntity")
    fun deleteAllProductCategory()

    @Query("select * from ProductEntity where ProductEntity.category=:categoryId")
    fun getProductByCategory(categoryId: String): List<ProductEntity>

    /*@Query("select * from ProductEntity where ProductEntity.deal='ok' and ProductEntity.productDealDays=:currentDate")
    fun getTodayDealProduct(currentDate: String): List<ProductEntity>*/

    @Query("select * from ProductEntity where ProductEntity.category IN(:categoryId)")
    fun getProductByCategoryList(categoryId: List<String>): List<ProductEntity>

    @Query("select * from ProductEntity where ProductEntity.productId=:productCode")
    fun getProductByCode(productCode: String): ProductEntity

    @Query("select distinct * from ProductCategoryEntity")
    fun getProductCategory(): List<ProductCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyCard(myCartEntity: MyCartEntity)

    @Query("select * from  MyCartEntity where MyCartEntity.productCode=:productCode")
    fun getQtyAndQtyValue(productCode: String): MyCartEntity

    @Query("select * from MyCartEntity where MyCartEntity.categoryCode=:categoryCode")
    fun getCartByCategory(categoryCode: String): List<MyCartEntity>

    @Query("select sum(subtotal) from MyCartEntity where MyCartEntity.categoryCode=:categoryCode")
    fun getMaxSubTotal(categoryCode: String): Double

    @Query("select * from ProductPriceEntity where ProductPriceEntity.product_id= :productCode and ProductPriceEntity.id= :priceID  and ProductPriceEntity.unit= :unit")
    fun getProductPriceByProductId(
        productCode: String,
        priceID: String,
        unit: String
    ): ProductPriceEntity

    @Query("select * from MyCartEntity where MyCartEntity.user_id=:userCode ")
    fun getMyCardList(userCode: String): List<MyCartEntity>

    //    change old query "select * from MyCartEntity"
    @Query("select * from MyCartEntity")
    fun getMyCardList(): List<MyCartEntity>

    //    change old query "SELECT COUNT(*) FROM MyCartEntity where MyCartEntity.productCode=:productCode"
    @Query("SELECT COUNT(*) FROM MyCartEntity where MyCartEntity.productCode=:productCode")
    fun getCartCount(productCode: String): Int

    //    change old query "select COUNT(*) from MyCartEntity"
    @Query("select COUNT(*) from MyCartEntity")
    fun getAllCartCount(): Int

    @Query("select * from MyCartEntity where MyCartEntity.user_id=:userCode and MyCartEntity.productCode=:productCode and MyCartEntity.productWight=:unit")
    fun getMyCardByProductCode(userCode: String, productCode: String, unit: String): MyCartEntity


    @Query("update MyCartEntity set discountAmount=:discountAmount,subtotal=:subtotal,tax=:tax,orderValue=:orderValue where MyCartEntity.user_id=:userCode and MyCartEntity.productCode=:productCode and MyCartEntity.productWight=:unit")
    fun updateMyCard(
        userCode: String,
        productCode: String,
        unit: String,
        discountAmount: Double,
        subtotal: Double,
        tax: Double,
        orderValue: Double
    )
    @Transaction
    @Query("update MyCartEntity set orderQty=:qty,subtotal=:qtyValue,discountAmount=:discount,tax=:tax,shipping_cost=:shippingCost where MyCartEntity.productCode=:productCode")
    fun updateMyCart(
        qty: Int,
        qtyValue: Double,
        productCode: String,
        discount: Double,
        tax: Double,
        shippingCost: Double
    )
    //    change old query "SELECT SUM(subtotal) FROM MyCartEntity"
    @Query("SELECT SUM(subtotal) FROM MyCartEntity")
    fun getSubTotal(): Double

    //    change old query "SELECT SUM(discountAmount) FROM MyCartEntity"
    @Query("SELECT SUM(discountAmount) FROM MyCartEntity")
    fun getTotalDiscount(): Double

    //    change old query "SELECT SUM(tax) FROM MyCartEntity"
    @Query("SELECT SUM(tax) FROM MyCartEntity")
    fun getTotalTax(): Double

    @Transaction
    @Query("delete from MyCartEntity where MyCartEntity.productCode=:productCode")
    fun deleteMyCardItem(productCode: String)

    @Query("delete from MyCartEntity")
    fun deleteAllMyCardItem()

    //    change old query "select sum(MyCartEntity.orderValue) from MyCartEntity where MyCartEntity.user_id=:userCode"
    @Query("select sum(c.orderValue) from ProductEntity p INNER JOIN MyCartEntity c ON c.productCode = p.productId where c.user_id=:userCode")
    fun getMyOrderAmount(userCode: String): Double

    //newly added by udhaya on 10-02-2026
    @Query("DELETE FROM MyCartEntity WHERE NOT EXISTS (SELECT 1 FROM ProductEntity WHERE MyCartEntity.productCode = ProductEntity.productId)")
    fun removeDeletedProduct()

    @Query("select sum(MyCartEntity.discountAmount) from MyCartEntity where MyCartEntity.user_id=:userCode")
    fun getMyOrderDiscountAmount(userCode: String): Double

    // change old query "select * from MyCartEntity where MyCartEntity.productCode=:productCode"
    @Query("select c.* from ProductEntity p INNER JOIN MyCartEntity c ON c.productCode = p.productId where c.productCode=:productCode")
    fun checkItemAlreadyExist(
        productCode: String
    ): MyCartEntity

    @Insert
    fun insertDeliveryAddress(deliveryAddressList: List<DeliveryAddressEntity>)

    @Query("select * from DeliveryAddressEntity")
    fun getDeliveryAddress(): List<DeliveryAddressEntity>

    @Query("delete from DeliveryAddressEntity")
    fun deleteAllDeliveryAddress()

    @Insert
    fun insertProductPriceDetails(priceList: List<ProductPriceEntity>)

    @Query("select * from ProductPriceEntity")
    fun getProductPriceList(): List<ProductPriceEntity>

    @Query("select * from ProductPriceEntity where ProductPriceEntity.product_id= :productCode")
    fun getProductPriceListByProductCode(productCode: String): List<ProductPriceEntity>

    @Query("select * from ProductPriceEntity where ProductPriceEntity.product_id= :productCode and ProductPriceEntity.id= :priceID  and ProductPriceEntity.unit= :unit")
    fun getProductPriceByPriceID(
        productCode: String,
        priceID: String,
        unit: String
    ): ProductPriceEntity

    @Query("delete from ProductPriceEntity")
    fun deleteProductPriceDetails()


}