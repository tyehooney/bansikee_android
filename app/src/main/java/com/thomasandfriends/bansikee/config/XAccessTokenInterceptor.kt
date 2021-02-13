package com.thomasandfriends.bansikee.config

import com.thomasandfriends.bansikee.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.thomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class XAccessTokenInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val jwtToken = mSharedPreferences?.getString(X_ACCESS_TOKEN, null)

        if (jwtToken != null)
            builder.addHeader(X_ACCESS_TOKEN, jwtToken)

        return chain.proceed(builder.build())
    }
}