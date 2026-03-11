package com.qbs.laafresh.data.repositories

import android.content.Context
import com.qbs.laafresh.data.network.api.IFavoriteApi
import com.qbs.laafresh.data.network.api.reponse.GetWishListResponse
import com.qbs.laafresh.data.network.api.reponse.SignUpResponse
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.data.utility.Provider
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess

interface IFavoriteRepository {

    fun getFavorite(
        onSuccess: OnSuccess<GetWishListResponse>,
        onError: OnError<Any>
    )
    fun deleteWishList(
        productId: String, onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    )




    companion object : Provider<IFavoriteRepository>() {
        override fun create(args: Array<out Any>): FavoriteRepository {
            if (args.size != 3) throw IllegalArgumentException("args size must be 4")

            val context = if (args[0] !is Context)
                throw IllegalArgumentException("args[0] is not Context")
            else
                args[0] as Context

            val api = if (args[1] !is IFavoriteApi)
                throw IllegalArgumentException("args[1] is not ArticleDao")
            else
                args[1] as IFavoriteApi

            val iPref = if (args[2] !is IPreferenceManager)
                throw IllegalArgumentException("args[2] is not IAPIWelcome")
            else
                args[2] as IPreferenceManager

            return FavoriteRepository(context, api,  iPref)
        }
    }
}