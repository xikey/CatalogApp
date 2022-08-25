package com.zikey.android.razancatalogapp.repo.apiClient

import android.content.Context
import com.zikey.android.razancatalogapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ServerApiClient {

    companion object {
        private var retrofitWithHeader: Retrofit? = null
        private var retrofitWithoutHeader: Retrofit? = null


        private fun initHeader(): OkHttpClient.Builder? {
            val httpClient = OkHttpClient.Builder()

            httpClient.connectTimeout(60, TimeUnit.SECONDS)
            httpClient.readTimeout(60, TimeUnit.SECONDS)
            httpClient.callTimeout(60, TimeUnit.SECONDS)
            httpClient.writeTimeout(60, TimeUnit.SECONDS)

            httpClient.addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("xAthorize", BuildConfig.TOKEN)
                    .method(original.method(), original.body())
                    .build()
                val response: Response = chain.proceed(request)

                response
            }


            httpClient.connectTimeout(30, TimeUnit.SECONDS);
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            return httpClient
        }


        fun getClient(): Retrofit? {
            return try {



                if (retrofitWithoutHeader == null) {
                    retrofitWithoutHeader = Retrofit.Builder()
                        .baseUrl(
                            BuildConfig.URL
                        )
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()
                }
                retrofitWithoutHeader
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }


        fun getClientWithHeader(): Retrofit? {
            if (retrofitWithHeader == null) {
                retrofitWithHeader = Retrofit.Builder()
                    .baseUrl(
                          BuildConfig.URL
                    )
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initHeader()!!.build())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
            }
            return retrofitWithHeader
        }


        fun clearRetrofit() {
            retrofitWithHeader = null
            retrofitWithoutHeader = null
        }

    }
}