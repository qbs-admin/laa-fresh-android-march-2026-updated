package com.qbs.laafresh.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.qbs.laafresh.data.network.api.IProductApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.AddWishListRequest
import com.qbs.laafresh.data.network.api.request.IndividualProductRequest
import com.qbs.laafresh.data.network.api.request.ProductDetailRequest
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.*
import android.util.Log

class ProductRepository(
    private val context: Context,
    private val iProductApi: IProductApi,
    public val iPref: IPreferenceManager
) : IProductRepository {
    private val mJob = SupervisorJob()
    private val mScope = CoroutineScope(Dispatchers.IO + mJob)

    override fun getProductDetails(
        productDetailRequest: ProductDetailRequest,
        onSuccess: OnSuccess<GetProductListResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iProductApi.getProductDetails(productDetailRequest).await()
                if (response.isSuccessful) {
                    response.body()?.let {
//                        Log.e("TAG", "----- LOG -----")
//                        Log.e("TAG",response.toString())

//                      iPref.setIsLoggedIn(true)
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

    override fun getIndividualProduct(
        productDetailRequest: IndividualProductRequest,
        onSuccess: OnSuccess<IndividualProductResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iProductApi.getIndividualProduct(productDetailRequest).await()
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

    override fun addWishList(
        productId: String,
        onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                var addWishListRequest=AddWishListRequest()
                addWishListRequest.userId=iPref.getCustomerId()
                addWishListRequest.productId=productId
                val response = iProductApi.addWishList(addWishListRequest).await()
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

    override fun deleteWishList(
        productId: String,
        onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                var addWishListRequest=AddWishListRequest()
                addWishListRequest.userId=iPref.getCustomerId()
                addWishListRequest.productId=productId
                val response = iProductApi.deleteWishList(addWishListRequest).await()
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

    override fun getAutoUpdate(onSuccess: OnSuccess<AutoUpdateResponse>, onError: OnError<Any>) {
        mScope.launch {
            try {

                val response = iProductApi.getAutoUpdate().await()
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