package com.qbs.laafresh.data.repositories

import android.content.Context
import com.qbs.laafresh.data.network.api.IProductApi
import com.qbs.laafresh.data.network.api.reponse.AutoUpdateResponse
import com.qbs.laafresh.data.network.api.reponse.GetProductListResponse
import com.qbs.laafresh.data.network.api.reponse.IndividualProductResponse
import com.qbs.laafresh.data.network.api.reponse.SignUpResponse
import com.qbs.laafresh.data.network.api.request.IndividualProductRequest
import com.qbs.laafresh.data.network.api.request.ProductDetailRequest
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.data.utility.Provider
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface IProductRepository {

    fun getProductDetails(
        productDetailRequest: ProductDetailRequest,
        onSuccess: OnSuccess<GetProductListResponse>,
        onError: OnError<Any>
    )

    fun getIndividualProduct(
        productDetailRequest: IndividualProductRequest,
        onSuccess: OnSuccess<IndividualProductResponse>,
        onError: OnError<Any>
    )

    fun addWishList(
        productId: String,
        onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    )

    fun deleteWishList(
        productId: String, onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    )

    fun getAutoUpdate(
        onSuccess: OnSuccess<AutoUpdateResponse>,
        onError: OnError<Any>
    )


    companion object : Provider<IProductRepository>() {
        override fun create(args: Array<out Any>): IProductRepository {
            if (args.size != 3) throw IllegalArgumentException("args size must be 4")

            val context = if (args[0] !is Context)
                throw IllegalArgumentException("args[0] is not Context")
            else
                args[0] as Context

            val api = if (args[1] !is IProductApi)
                throw IllegalArgumentException("args[1] is not ArticleDao")
            else
                args[1] as IProductApi

            val iPref = if (args[2] !is IPreferenceManager)
                throw IllegalArgumentException("args[2] is not IAPIWelcome")
            else
                args[2] as IPreferenceManager

            return ProductRepository(context, api, iPref)
        }
    }
}