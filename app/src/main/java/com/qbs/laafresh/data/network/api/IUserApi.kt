package com.qbs.laafresh.data.network.api

import com.qbs.laafresh.data.network.api.IUserApi.Companion.SLIDES
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.LoginRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface IUserApi {

    @POST(LOG_IN)
    fun userLogin(
        @Body loginRequest: LoginRequest
    ): Deferred<Response<LoginResponse>>


    @POST(SUB_CAT_ITEM)
    fun getSubCategories(): Deferred<Response<SubCategoriesResponse>>

    @POST(SLIDES)
    fun getSlides():Deferred<Response<SlidersResponse>>

    @POST(BUNDLE)
    fun getProductBundle():Deferred<Response<OfferResponse>>


      @POST(MINI_ORDER)
    fun getMiniOrder():Deferred<Response<MiniOrderResponse>>

    @GET(PRODUCT)
    fun getProduct(): Deferred<Response<GetProductListResponse>>

    @POST(FOOTER_BANNER)
    fun getFooterBannerResponse(): Deferred<Response<FooterBannerResponse>>

    companion object {
        const val LOG_IN: String = "login.php"
        const val SUB_CAT_ITEM: String = "subcategories.php"
        const val SLIDES: String = "slides.php"
        const val BUNDLE: String = "product_bundle.php"
        const val MINI_ORDER: String = "isminimumorder.php"
        const val PRODUCT: String = "products.php"
        const val FOOTER_BANNER: String = "banner.php"

    }
}