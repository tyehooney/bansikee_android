package com.tomasandfriends.bansikee

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.tomasandfriends.bansikee.config.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object{
        var instance : ApplicationClass? = null

        var mSharedPreferences : SharedPreferences? = null
        const val SP_TAG = "SP_BANSIKEE"

        //Base URL
        const val BASE_URL = "https://bansikee.site"

        // JWT Token Key
        const val X_ACCESS_TOKEN : String = "x-access-token"

        // 카카오 Token Key
        const val KAKAO_ACCESS_TOKEN = "KAKAO_ACCESS_TOKEN"

        // Retrofit 인스턴스
        var retrofit : Retrofit? = null

        fun initRetrofit() : Retrofit {
            if(retrofit == null){
                val client = OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
                    .build()

                val gson = GsonBuilder().setLenient().create()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (mSharedPreferences == null)
            mSharedPreferences = applicationContext.getSharedPreferences(SP_TAG, MODE_PRIVATE)
    }
}