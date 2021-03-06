package com.tomasandfriends.bansikee.src.activities.login

import com.tomasandfriends.bansikee.ApplicationClass.Companion.CODE_SUCCESS
import com.tomasandfriends.bansikee.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.login.interfaces.LoginRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.login.interfaces.LoginView
import com.tomasandfriends.bansikee.src.activities.login.models.AccessObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(loginView: LoginView) {
    private val mLoginView = loginView

    fun googleLogin(idToken : String){
        val retroInterface = initRetrofit().create(LoginRetrofitInterface::class.java)

        retroInterface.googleLogin(AccessObject(idToken)).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSharedPreferences!!.edit().putString(X_ACCESS_TOKEN, apiResponse!!.data).apply()
                    mLoginView.socialLoginSuccess()
                } else {
                    mLoginView.socialLoginFailed(if(response.body() == null) null else response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mLoginView.socialLoginFailed(null)
            }
        })
    }

    fun kakaoLogin(accessToken : String){
        val retroInterface = initRetrofit().create(LoginRetrofitInterface::class.java)

        retroInterface.kakaoLogin(AccessObject(accessToken)).enqueue(object :Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSharedPreferences!!.edit().putString(X_ACCESS_TOKEN, apiResponse!!.data).apply()
                    mLoginView.socialLoginSuccess()
                } else {
                    mLoginView.socialLoginFailed(if(response.body() == null) null else response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mLoginView.socialLoginFailed(null)
            }
        })
    }
}