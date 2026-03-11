package com.qbs.laafresh.data.repositories

import android.content.Context
import com.qbs.laafresh.data.network.api.IUserApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.LoginRequest
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.data.utility.Provider
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess

interface IUserRepository {


    fun userLogin(
        reqLogin: LoginRequest,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<Any>
    )

    fun getSubCategories(
        onSuccess: OnSuccess<SubCategoriesResponse>,
        onError: OnError<Any>
    )
    fun getSlides(
        onSuccess: OnSuccess<SlidersResponse>,
        onError: OnError<Any>
    )

    fun getProductBundle(
        onSuccess: OnSuccess<OfferResponse>,
        onError: OnError<Any>
    )


    fun getMiniOrder(
        onSuccess: OnSuccess<MiniOrderResponse>,
        onError: OnError<Any>
    )

    fun getProduct(
        onSuccess: OnSuccess<GetProductListResponse>,
        onError: OnError<Any>
    )

    fun getFooterBannerResponse(
        onSuccess: OnSuccess<FooterBannerResponse>,
        onError: OnError<Any>)



    companion object : Provider<UserRepository>() {
        override fun create(args: Array<out Any>): UserRepository {
            if (args.size != 3) throw IllegalArgumentException("args size must be 4")

            val context = if (args[0] !is Context)
                throw IllegalArgumentException("args[0] is not Context")
            else
                args[0] as Context

            val api = if (args[1] !is IUserApi)
                throw IllegalArgumentException("args[1] is not ArticleDao")
            else
                args[1] as IUserApi

            val iPref = if (args[2] !is IPreferenceManager)
                throw IllegalArgumentException("args[2] is not IAPIWelcome")
            else
                args[2] as IPreferenceManager

            return UserRepository(context, api,  iPref)
        }
    }
}