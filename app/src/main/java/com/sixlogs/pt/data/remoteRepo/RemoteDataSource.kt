package com.sixlogs.pt.data.remoteRepo

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RemoteDataSource {

    companion object {
        private const val BASE_URl =
            "https://api.aladhan.com/"
          //  "https://api.aladhan.com/v1/timingsByAddress?address=Karachi,Pakistan"
           // "https://api.pray.zone/v2/times/"
//https://api.pray.zone/v2/times/tomorrow.json?city=mecca&juristic=0
        //base: https://api.pray.zone/v2/times/today.json?city=karachi

    }

    fun <Api> buildApi_(
        api: Class<Api>,): Api {
        val httpClientBuilder = OkHttpClient.Builder()
        /*      httpClientBuilder.addInterceptor(Interceptor { chain ->
                  val requestBuilder = chain.request().newBuilder()
                  requestBuilder.header("Authorization", "Bearer $sessionToken")
                  requestBuilder.header("DeviceId", "$deviceId")
                  Log.d("112", "buildApi: ${"Bearer $sessionToken, DeviceId:$deviceId"}")
                   chain.proceed(requestBuilder.build())
              })
      */
        OkHttpClient.Builder().also { client ->
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URl)
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)


    }

    fun <Api> buildApi(api: Class<Api>, sessionToken: String ?= null): Api {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Authorization", "Bearer $sessionToken")
            Log.d("112", "buildApi: ${"Bearer $sessionToken"}")

            //  requestBuilder.header("DeviceId", "$deviceId")
            chain.proceed(requestBuilder.build())
        })

        OkHttpClient.Builder().also { client ->
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        return   Retrofit.Builder()
            .baseUrl(BASE_URl)
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    fun <Api> buildLongRequestApi(
        api: Class<Api>,
        deviceId: String? = null,
        sessionToken: String? = null
    ): Api {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(15, TimeUnit.SECONDS);
        /*httpClientBuilder.addInterceptor(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Authorization", "Bearer $sessionToken")
            requestBuilder.header("DeviceId", "$deviceId")
            Log.d("112", "buildApi: ${"Bearer $sessionToken, DeviceId:$deviceId"}")
            chain.proceed(requestBuilder.build())
        })*/

        OkHttpClient.Builder().also { client ->
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URl)
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)


    }


}