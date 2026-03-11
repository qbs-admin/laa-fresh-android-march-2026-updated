package com.qbs.laafresh.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.qbs.laafresh.data.network.api.IUserApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.LoginRequest
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.*

class UserRepository(
    private val context: Context,
    private val iUserApi: IUserApi,
    public val iPref: IPreferenceManager
) : IUserRepository {


    private val mJob = SupervisorJob()
    private val mScope = CoroutineScope(Dispatchers.IO + mJob)


    override fun userLogin(
        reqLogin: LoginRequest,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iUserApi.userLogin(reqLogin).await()
                if (response.isSuccessful) {
                    response.body()?.let {


//                        iPref.setIsLoggedIn(true)
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

    override fun getSubCategories(
        onSuccess: OnSuccess<SubCategoriesResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iUserApi.getSubCategories().await()
                if (response.isSuccessful) {
                    response.body()?.let {


//                        iPref.setIsLoggedIn(true)
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

    override fun getSlides(onSuccess: OnSuccess<SlidersResponse>, onError: OnError<Any>) {
        mScope.launch {
            try {
                val response = iUserApi.getSlides().await()
                if (response.isSuccessful) {
                    response.body()?.let {


//                        iPref.setIsLoggedIn(true)
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

    override fun getProductBundle(
        onSuccess: OnSuccess<OfferResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iUserApi.getProductBundle().await()
                if (response.isSuccessful) {
                    response.body()?.let {


//                        iPref.setIsLoggedIn(true)
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

    override fun getMiniOrder(onSuccess: OnSuccess<MiniOrderResponse>, onError: OnError<Any>) {
        mScope.launch {
            try {
                val response = iUserApi.getMiniOrder().await()
                if (response.isSuccessful) {
                    response.body()?.let {


                      iPref.setMiniAmount(it.orderMinAmount.toString())
                      iPref.setShippingCost(it.shippingCost.toString())
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

    override fun getProduct(onSuccess: OnSuccess<GetProductListResponse>, onError: OnError<Any>) {

        mScope.launch {
            try {
                val response = iUserApi.getProduct().await()
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
    override fun getFooterBannerResponse(
        onSuccess: OnSuccess<FooterBannerResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iUserApi.getFooterBannerResponse().await()
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