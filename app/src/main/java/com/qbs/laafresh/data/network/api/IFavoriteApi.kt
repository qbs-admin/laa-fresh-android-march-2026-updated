package com.qbs.laafresh.data.network.api

import com.qbs.laafresh.data.network.api.reponse.GetWishListResponse
import com.qbs.laafresh.data.network.api.reponse.SignUpResponse
import com.qbs.laafresh.data.network.api.request.AddWishListRequest
import com.qbs.laafresh.data.network.api.request.GetWishListRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface IFavoriteApi {

    @POST(GET_WISH_LISTS)
    fun getFavorite(
        @Body getWishListRequest: GetWishListRequest
    ): Deferred<Response<GetWishListResponse>>

    @POST(IProductApi.DELETE_WISH_LISTS)
    fun deleteWishList(@Body deleteWishListRequest: AddWishListRequest): Deferred<Response<SignUpResponse>>

    companion object {
        const val GET_WISH_LISTS: String = "wishlists.php"
        const val DELETE_WISH_LISTS: String = "remove_wish.php"

    }
}