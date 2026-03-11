package com.qbs.laafresh.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.qbs.laafresh.data.network.api.IRegisterApi
import com.qbs.laafresh.data.network.api.reponse.LoginResponse
import com.qbs.laafresh.data.network.api.reponse.ResMessage
import com.qbs.laafresh.data.network.api.reponse.SignUpResponse
import com.qbs.laafresh.data.network.api.request.ForgotPasswordRequest
import com.qbs.laafresh.data.network.api.request.SignUpRequest
import com.qbs.laafresh.data.preference.IPreferenceManager
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import kotlinx.coroutines.*

class RegisterRepository(
    private val context: Context,
    private val iRegisterApi: IRegisterApi,
    public val iPref: IPreferenceManager
) : IRegisterRepository {

    private val mJob = SupervisorJob()
    private val mScope = CoroutineScope(Dispatchers.IO + mJob)

    override fun signUp(
        signUpRequest: SignUpRequest,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iRegisterApi.signUp(signUpRequest).await()
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

    override fun forgotPassword(
        forgotPassword: ForgotPasswordRequest,
        onSuccess: OnSuccess<SignUpResponse>,
        onError: OnError<Any>
    ) {
        mScope.launch {
            try {
                val response = iRegisterApi.forgotPassword(forgotPassword).await()
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


}