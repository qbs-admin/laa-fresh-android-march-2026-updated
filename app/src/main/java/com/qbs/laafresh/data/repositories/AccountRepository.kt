package com.qbs.laafresh.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.qbs.laafresh.data.network.api.IAccountApi
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.*
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.*

class AccountRepository(
    private val context: Context,
    private val iAccountApi: IAccountApi,
    private val iPref: IPreferenceManager
) : IAccountRepository {


    private val mJob = SupervisorJob()
    private val mScope = CoroutineScope(Dispatchers.IO + mJob)
    override fun addAddress(
        addAddressRequest: AddAddressRequest,
        onSuccess: OnSuccess<AddAddressResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iAccountApi.addAddress(addAddressRequest).await()
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

    override fun editProfile(
        editProfileRequest: EditProfileRequest,
        onSuccess: OnSuccess<EditProfileResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iAccountApi.editProfile(editProfileRequest).await()
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

    override fun getProfile(onSuccess: OnSuccess<GetProfileResponse>, onError: OnError<Any>) {
        mScope.launch {
            try {
                val getWishListRequest = GetWishListRequest()
                getWishListRequest.userId = iPref.getCustomerId()
                val response = iAccountApi.getProfile(getWishListRequest).await()
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

    override fun getAddress(onSuccess: OnSuccess<GetAddressResponse>, onError: OnError<Any>) {
        mScope.launch {
            try {
                val getWishListRequest = GetWishListRequest()
                getWishListRequest.userId = iPref.getCustomerId()
                val response = iAccountApi.getAddress(getWishListRequest).await()
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

    override fun pinCode(
        pincodeRequest: PincodeRequest,
        onSuccess: OnSuccess<PincodeResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iAccountApi.pinCode(pincodeRequest).await()
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

    override fun getAddressDetails(
        getAddressDetailsRequest: GetAddressDetailsRequest,
        onSuccess: OnSuccess<GetDefaultAddressResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iAccountApi.getAddressDetails(getAddressDetailsRequest).await()
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

    override fun updateAddress(
        updateAddressRequest: UpdateAddressRequest,
        onSuccess: OnSuccess<AddAddressResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iAccountApi.updateAddress(updateAddressRequest).await()
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

    override fun deleteAddress(
        getAddressDetailsRequest: GetAddressDetailsRequest,
        onSuccess: OnSuccess<AddAddressResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iAccountApi.deleteAddress(getAddressDetailsRequest).await()
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