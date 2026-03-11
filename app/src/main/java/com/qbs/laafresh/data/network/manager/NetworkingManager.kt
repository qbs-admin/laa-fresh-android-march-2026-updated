package com.qbs.laafresh.data.network.manager

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.qbs.laafresh.BuildConfig
import com.qbs.laafresh.data.utility.Provider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkingManager(private val context: Context) :
    INetworkingManager {
    override val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            // Add logging interceptor
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            addNetworkInterceptor(StethoInterceptor())
        }.callTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    companion object : Provider<NetworkingManager>() {
        override fun create(args: Array<out Any>): NetworkingManager {
            if (args.isEmpty()) throw IllegalArgumentException("NetworkingManager need a context")
            if (args[0] !is Context) throw IllegalArgumentException("args[0] must be a Context")
            return NetworkingManager(args[0] as Context)
        }

        inline fun <reified T> createApi(context: Context): T {
            return if (T::class.java.isInterface) {
                Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().setLenient().create()
                        )
                    )
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(NetworkingManager(context).okHttpClient)
                    .build().create(T::class.java)
            } else throw IllegalArgumentException("${T::class.java.simpleName} is not an interface")
        }
    }
}

interface INetworkingManager {

    val okHttpClient: OkHttpClient

}