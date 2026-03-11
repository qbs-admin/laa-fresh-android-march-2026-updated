package com.qbs.laafresh.data.network.api

import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.AddWishListRequest
import com.qbs.laafresh.data.network.api.request.IndividualProductRequest
import com.qbs.laafresh.data.network.api.request.ProductDetailRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IProductApi {
    @POST(GET_PRODUCT_DETAIL)
    fun getProductDetails(@Body request: ProductDetailRequest): Deferred<Response<GetProductListResponse>>

    @POST(GET_PRODUCT)
    fun getIndividualProduct(@Body request: IndividualProductRequest): Deferred<Response<IndividualProductResponse>>

    @POST(ADD_WISH_LISTS)
    fun addWishList(@Body addWishListRequest: AddWishListRequest): Deferred<Response<SignUpResponse>>

    @POST(DELETE_WISH_LISTS)
    fun deleteWishList(@Body deleteWishListRequest: AddWishListRequest): Deferred<Response<SignUpResponse>>

    @POST(VERSION)
    fun getAutoUpdate(): Deferred<Response<AutoUpdateResponse>>



    companion object {
        const val GET_PRODUCT_DETAIL: String = "getproductlist.php"
        const val GET_PRODUCT: String = "product_detail.php"
        const val ADD_WISH_LISTS: String = "add_wish.php"
        const val DELETE_WISH_LISTS: String = "remove_wish.php"
        const val VERSION: String = "version.php"
    }
}