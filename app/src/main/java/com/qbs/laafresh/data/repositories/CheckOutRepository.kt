package com.qbs.laafresh.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.qbs.laafresh.data.network.api.ICheckOutApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.*
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.*

class CheckOutRepository(
    private val context: Context,
    private val iCheckOurApi: ICheckOutApi,
    private val iPref: IPreferenceManager
) : ICheckOutRepository {


    private val mJob = SupervisorJob()
    private val mScope = CoroutineScope(Dispatchers.IO + mJob)
    override fun getDefaultAddress(
        onSuccess: OnSuccess<GetDefaultAddressResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val getWishListRequest = GetWishListRequest()
                getWishListRequest.userId = iPref.getCustomerId()
                val response = iCheckOurApi.getdefaultaddress(getWishListRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ResMessage::class.java
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                }

            }
        }
    }

    override fun placingOrders(
        sendCartDataRequest: SendCartDataRequest,
        onSuccess: OnSuccess<SalesResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val getWishListRequest = GetWishListRequest()
                getWishListRequest.userId = iPref.getCustomerId()
                val response = iCheckOurApi.placingOrders(sendCartDataRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ResMessage::class.java
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                }

            }
        }
    }

    override fun getMyOrderList(
        onSuccess: OnSuccess<MyOrderListResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val myOrderRequest = MyOrderRequest()
                myOrderRequest.id = iPref.getCustomerId()
                val response = iCheckOurApi.getMyOrderList(myOrderRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ResMessage::class.java
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                }

            }
        }
    }

    override fun getCoupon(
        couponRequest: CouponRequest,
        onSuccess: OnSuccess<GetCouponResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iCheckOurApi.getCoupon(couponRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ResMessage::class.java
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                }

            }
        }
    }

    override fun appliedReferralDiscount(
        onSuccess: OnSuccess<ReferralResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val getReferralRequest = GetReferralRequest()
                getReferralRequest.referer_id = iPref.getCustomerId()
                val response = iCheckOurApi.appliedReferralDiscount(getReferralRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ResMessage::class.java
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                }

            }
        }
    }

    override fun instaMojoPayment(
        instaMojoRequest: InstaMojoRequest,
        onSuccess: OnSuccess<InstaMojoResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val getWishListRequest = GetWishListRequest()
                getWishListRequest.userId = iPref.getCustomerId()
                val response = iCheckOurApi.instaMojoPayment(instaMojoRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ResMessage::class.java
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                }

            }
        }
    }

    override fun getDeliveryTimes(
        deliveryTimeRequest: DeliveryTimeRequest,
        onSuccess: OnSuccess<DeliveryTimeResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iCheckOurApi.getDeliveryTimes(deliveryTimeRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ResMessage::class.java
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                }

            }
        }
    }


}