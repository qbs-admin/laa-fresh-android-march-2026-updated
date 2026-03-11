package com.qbs.laafresh.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.data.network.api.IRegisterApi
import com.qbs.laafresh.data.network.api.reponse.LoginResponse
import com.qbs.laafresh.data.network.api.reponse.SignUpResponse
import com.qbs.laafresh.data.network.api.request.ForgotPasswordRequest
import com.qbs.laafresh.data.network.api.request.SignUpRequest
import com.qbs.laafresh.data.network.manager.NetworkingManager
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.repositories.IRegisterRepository
import com.qbs.laafresh.ui.component.OnError
import com.qbs.laafresh.ui.component.OnSuccess
import org.jetbrains.annotations.NotNull

class VMRegister(private val context: Context, @NotNull private val repository: IRegisterRepository) :
    ViewModel() {

    fun signUp(
        userLogin: String, etLastName: String,
        etEmail: String, etPassword: String,
        etMobile: String,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<Any>
    ) {
        val reqLogin = SignUpRequest()
        reqLogin.userName = userLogin
        reqLogin.surName = etLastName
        reqLogin.email = etEmail
        reqLogin.password = etPassword
        reqLogin.phone = etMobile
        repository.signUp(reqLogin, onSuccess, onError)
    }
    fun forgotPassword(requestEmail: String, onSuccess: OnSuccess<SignUpResponse>,
                       onError: OnError<Any>){
        val ForgotPassword = ForgotPasswordRequest()
        ForgotPassword.email=requestEmail
        repository.forgotPassword(ForgotPassword, onSuccess, onError)

    }


    class Factory(
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {


        private val repository = IRegisterRepository.get(
            context,
//            database,
            NetworkingManager.createApi<IRegisterApi>(context),
            PreferenceManager(context)
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST") return VMRegister(context, repository) as T
        }
    }
}