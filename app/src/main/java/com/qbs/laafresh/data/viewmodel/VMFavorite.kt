package com.qbs.laafresh.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.data.network.api.IFavoriteApi
import com.qbs.laafresh.data.network.api.reponse.GetWishListResponse
import com.qbs.laafresh.data.network.api.reponse.SignUpResponse
import com.qbs.laafresh.data.network.manager.NetworkingManager
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.repositories.IFavoriteRepository
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import org.jetbrains.annotations.NotNull

class VMFavorite(
    private val context: Context,
    @NotNull private val repository: IFavoriteRepository
) :
    ViewModel() {

    fun getFavorite(
        onSuccess: OnSuccess<GetWishListResponse>,
        onError: OnError<Any>
    ) {
        repository.getFavorite( onSuccess, onError)
    }

    fun deleteWishList(
        productId: String, onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    ){
        repository.deleteWishList(productId,onSuccess,onError)
    }

    class Factory(
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {


        private val repository = IFavoriteRepository.get(
            context,
//            database,
            NetworkingManager.createApi<IFavoriteApi>(context),
            PreferenceManager(context)
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST") return VMFavorite(context, repository) as T
        }
    }
}