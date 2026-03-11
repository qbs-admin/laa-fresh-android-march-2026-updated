package com.qbs.laafresh.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.qbs.laafresh.data.network.api.IFavoriteApi
import com.qbs.laafresh.data.network.api.reponse.GetWishListResponse
import com.qbs.laafresh.data.network.api.reponse.ResMessage
import com.qbs.laafresh.data.network.api.reponse.SignUpResponse
import com.qbs.laafresh.data.network.api.request.AddWishListRequest
import com.qbs.laafresh.data.network.api.request.GetWishListRequest
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.*

class FavoriteRepository(
    private val context: Context,
    private val iFavoriteApi: IFavoriteApi,
    private val iPref: IPreferenceManager
) : IFavoriteRepository {

    private val mJob = SupervisorJob()
    private val mScope = CoroutineScope(Dispatchers.IO + mJob)

    override fun getFavorite(
        onSuccess: OnSuccess<GetWishListResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val getWishListRequest = GetWishListRequest()
                getWishListRequest.userId = iPref.getCustomerId()
                val response = iFavoriteApi.getFavorite(getWishListRequest).await()
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

    override fun deleteWishList(
        productId: String,
        onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                var addWishListRequest= AddWishListRequest()
                addWishListRequest.userId=iPref.getCustomerId()
                addWishListRequest.productId=productId
                val response = iFavoriteApi.deleteWishList(addWishListRequest).await()
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