package com.qbs.laafresh.data.repositories

import android.content.Context
import com.qbs.laafresh.data.network.api.ICheckOutApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.*
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.data.utility.Provider
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body

interface ICheckOutRepository {
    fun getDefaultAddress(
        onSuccess: OnSuccess<GetDefaultAddressResponse>,
        onError: OnError<Any>
    )

    fun placingOrders(
        sendCartDataRequest: SendCartDataRequest,
        onSuccess: OnSuccess<SalesResponse>,
        onError: OnError<Any>
    )

    fun getMyOrderList(
        onSuccess: OnSuccess<MyOrderListResponse>,
        onError: OnError<Any>
    )

    fun getCoupon(
        couponRequest: CouponRequest,
        onSuccess: OnSuccess<GetCouponResponse>,
        onError: OnError<Any>
    )

    fun appliedReferralDiscount(
        onSuccess: OnSuccess<ReferralResponse>,
        onError: OnError<Any>
    )

    fun instaMojoPayment(
        instaMojoRequest: InstaMojoRequest,
        onSuccess: OnSuccess<InstaMojoResponse>,
        onError: OnError<Any>
    )

    fun getDeliveryTimes(
        deliveryTimeRequest: DeliveryTimeRequest,
        onSuccess: OnSuccess<DeliveryTimeResponse>,
        onError: OnError<Any>
    )


    companion object : Provider<ICheckOutRepository>() {
        override fun create(args: Array<out Any>): CheckOutRepository {
            if (args.size != 3) throw IllegalArgumentException("args size must be 4")

            val context = if (args[0] !is Context)
                throw IllegalArgumentException("args[0] is not Context")
            else
                args[0] as Context

            val api = if (args[1] !is ICheckOutApi)
                throw IllegalArgumentException("args[1] is not ArticleDao")
            else
                args[1] as ICheckOutApi

            val iPref = if (args[2] !is IPreferenceManager)
                throw IllegalArgumentException("args[2] is not IAPIWelcome")
            else
                args[2] as IPreferenceManager

            return CheckOutRepository(context, api, iPref)
        }
    }
}