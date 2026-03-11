package com.qbs.laafresh.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.data.network.api.ICheckOutApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.*
import com.qbs.laafresh.data.network.manager.NetworkingManager
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.repositories.ICheckOutRepository
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import org.jetbrains.annotations.NotNull

class VMCheckOut(
    private val context: Context,
    @NotNull private val repository: ICheckOutRepository
) : ViewModel() {
    fun getDefaultAddress(
        onSuccess: OnSuccess<GetDefaultAddressResponse>,
        onError: OnError<Any>
    ) {
        repository.getDefaultAddress(onSuccess, onError)
    }

    fun placingOrders(
        sendCartDataRequest: SendCartDataRequest,
        onSuccess: OnSuccess<SalesResponse>,
        onError: OnError<Any>
    ) {
        repository.placingOrders(sendCartDataRequest, onSuccess, onError)
    }

    fun getMyOrderList(
        onSuccess: OnSuccess<MyOrderListResponse>,
        onError: OnError<Any>
    ) {
        repository.getMyOrderList(onSuccess, onError)
    }

    fun getCoupon(
        couponRequest: CouponRequest,
        onSuccess: OnSuccess<GetCouponResponse>,
        onError: OnError<Any>
    ) {
        repository.getCoupon(couponRequest, onSuccess, onError)
    }

    fun appliedReferralDiscount(
        onSuccess: OnSuccess<ReferralResponse>,
        onError: OnError<Any>
    ) {
        repository.appliedReferralDiscount(onSuccess, onError)
    }

    fun instaMojoPayment(
        instaMojoRequest: InstaMojoRequest,
        onSuccess: OnSuccess<InstaMojoResponse>,
        onError: OnError<Any>
    ) {
        repository.instaMojoPayment(instaMojoRequest, onSuccess, onError)
    }

    fun getDeliveryTimes(
        deliveryTimeRequest: DeliveryTimeRequest,
        onSuccess: OnSuccess<DeliveryTimeResponse>,
        onError: OnError<Any>
    ){
        repository.getDeliveryTimes(deliveryTimeRequest, onSuccess, onError)
    }

    class Factory(
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {
        private val repository = ICheckOutRepository.get(
            context,
//            database,
            NetworkingManager.createApi<ICheckOutApi>(context),
            PreferenceManager(context)
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST") return VMCheckOut(context, repository) as T
        }
    }


}