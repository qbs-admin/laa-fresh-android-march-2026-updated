package com.qbs.laafresh.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.data.network.api.IUserApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.LoginRequest
import com.qbs.laafresh.data.network.manager.NetworkingManager
import com.qbs.laafresh.data.repositories.IUserRepository
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import org.jetbrains.annotations.NotNull

class VMUser(private val context: Context, @NotNull private val repository: IUserRepository) :
    ViewModel() {

    fun userLogin(
        emailId: String, password: String,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<Any>
    ) {
        val reqLogin = LoginRequest()
        reqLogin.email = emailId
        reqLogin.password = password
        repository.userLogin(reqLogin, onSuccess, onError)
    }

    fun getSubCategories(
        onSuccess: OnSuccess<SubCategoriesResponse>,
        onError: OnError<Any>
    ) {
        repository.getSubCategories(onSuccess, onError)
    }

    fun getSlides(
        onSuccess: OnSuccess<SlidersResponse>,
        onError: OnError<Any>
    ) {
        repository.getSlides(onSuccess, onError)
    }
    fun getProductBundle(
        onSuccess: OnSuccess<OfferResponse>,
        onError: OnError<Any>
    ) {
        repository.getProductBundle(onSuccess, onError)
    }

    fun getMiniOrder(onSuccess: OnSuccess<MiniOrderResponse>, onError: OnError<Any>){
        repository.getMiniOrder(onSuccess, onError)
    }

    fun getProduct(onSuccess: OnSuccess<GetProductListResponse>, onError: OnError<Any>){
        repository.getProduct(onSuccess, onError)
    }

    fun getFooterBannerResponse(
        onSuccess: OnSuccess<FooterBannerResponse>,
        onError: OnError<Any>
    ){
        repository.getFooterBannerResponse(onSuccess,onError)
    }


    class Factory(
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {


        private val repository = IUserRepository.get(
            context,
//            database,
            NetworkingManager.createApi<IUserApi>(context),
            PreferenceManager(context)
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST") return VMUser(context, repository) as T
        }
    }
}