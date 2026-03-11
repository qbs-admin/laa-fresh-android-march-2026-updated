package com.qbs.laafresh.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.data.network.api.IProductApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.*
import com.qbs.laafresh.data.network.manager.NetworkingManager
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.repositories.IProductRepository
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import org.jetbrains.annotations.NotNull

class VMProduct(private val context: Context, @NotNull private val repository: IProductRepository) :
    ViewModel() {

    fun getProductDetails(
        product_id: String,
        onSuccess: OnSuccess<GetProductListResponse>,
        onError: OnError<Any>
    ) {
        val getProductListResponse = ProductDetailRequest()
        getProductListResponse.product_id = product_id
        repository.getProductDetails(getProductListResponse, onSuccess, onError)
    }

    fun getIndividualProduct(
        product_id: String,
        user_id: String,
        onSuccess: OnSuccess<IndividualProductResponse>,
        onError: OnError<Any>
    ) {
        val individualProductRequest = IndividualProductRequest()
        individualProductRequest.product_id = product_id
        individualProductRequest.userid = user_id
        repository.getIndividualProduct(individualProductRequest, onSuccess, onError)
    }

    fun addWishList(
        productId: String,
        onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    ) {
        repository.addWishList(productId, onSuccess, onError)
    }

    fun deleteWishList(
        productId: String, onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    ) {
        repository.deleteWishList(productId, onSuccess, onError)
    }

    fun getAutoUpdate(
        onSuccess: OnSuccess<AutoUpdateResponse>,
        onError: OnError<Any>
    ) {
        repository.getAutoUpdate(onSuccess, onError)
    }


    class Factory(
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {


        private val repository = IProductRepository.get(
            context,
//            database,
            NetworkingManager.createApi<IProductApi>(context),
            PreferenceManager(context)
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST") return VMProduct(context, repository) as T
        }
    }
}