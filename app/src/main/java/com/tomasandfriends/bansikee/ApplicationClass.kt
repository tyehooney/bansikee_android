package com.tomasandfriends.bansikee

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.tomasandfriends.bansikee.config.XAccessTokenInterceptor
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object{
        var instance : ApplicationClass? = null

        var mSharedPreferences : SharedPreferences? = null
        const val SP_TAG = "SP_BANSIKEE"

        //Base URL
        const val BASE_URL = "http://3.34.126.187/"

        // JWT Token Key
        const val X_ACCESS_TOKEN : String = "X-AUTH-TOKEN"

        const val USER_EMAIL = "USER_EMAIL"
        const val USER_NAME = "USER_NAME"

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

        const val CODE_SUCCESS = 200

        const val NETWORK_ERROR = "네트워크 에러"

        fun getErrorResponse(errorBody: ResponseBody): DefaultResponse? {
            return retrofit!!.responseBodyConverter<DefaultResponse>(
                    DefaultResponse::class.java,
                    DefaultResponse::class.java.annotations)
                    .convert(errorBody)
        }
    }

    private val TAG = javaClass.name

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (mSharedPreferences == null)
            mSharedPreferences = applicationContext.getSharedPreferences(SP_TAG, MODE_PRIVATE)

        //Getting KeyHash
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//            Log.d(TAG, "keyHash : ${getKeyHash(this)}")
//        }

        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        //Firebase 초기화
        FirebaseApp.initializeApp(this)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getKeyHash(context : Context) : String? {
        var key : String? = null
        val packageInfo = packageManager
                .getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        val signatures = packageInfo.signingInfo.apkContentsSigners
        for (signature in signatures){
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            key = String(Base64.encode(md.digest(), 0))
        }

        return key
    }
}