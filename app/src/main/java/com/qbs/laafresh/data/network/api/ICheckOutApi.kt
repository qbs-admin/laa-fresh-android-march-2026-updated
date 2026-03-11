package com.qbs.laafresh.data.network.api

import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ICheckOutApi {

    @POST(GET_DEFAULT_ADDRESS)
    fun getdefaultaddress(
        @Body getWishListRequest: GetWishListRequest
    ): Deferred<Response<GetDefaultAddressResponse>>

    @POST(GET_DELIVERY_TIME)
    fun getDeliveryTimes(
        @Body deliveryTimeRequest: DeliveryTimeRequest
    ): Deferred<Response<DeliveryTimeResponse>>

    @POST(SALES)
    fun placingOrders(
        @Body sendCartDataRequest: SendCartDataRequest
    ): Deferred<Response<SalesResponse>>

    @POST(GET_MY_ORDER_LIST)
    fun getMyOrderList(@Body myOrderRequest: MyOrderRequest): Deferred<Response<MyOrderListResponse>>

    @POST(GET_COUPON_DETAILS)
    fun getCoupon(@Body couponRequest: CouponRequest): Deferred<Response<GetCouponResponse>>

    @POST(GET_REFERRAL_DISCOUNT)
    fun appliedReferralDiscount(@Body getReferralRequest: GetReferralRequest): Deferred<Response<ReferralResponse>>

    @POST(INSTAMOJO)
    fun instaMojoPayment(@Body instaMojoRequest: InstaMojoRequest): Deferred<Response<InstaMojoResponse>>

    companion object {
        const val GET_DEFAULT_ADDRESS: String = "getdefaultaddress.php"
        const val SALES: String = "sales.php"
        const val GET_MY_ORDER_LIST: String = "orders.php"
        const val GET_COUPON_DETAILS: String = "getcoupon_details.php"
        const val GET_REFERRAL_DISCOUNT: String = "getreferraldiscount.php"
        const val GET_DELIVERY_TIME: String = "getdeliverytimes.php"
        const val INSTAMOJO: String = "instamojo.php"

    }
}