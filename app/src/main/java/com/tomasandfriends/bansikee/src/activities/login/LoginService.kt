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
                if (response.isSuccessful && response.body() != null){
                    val apiResponse = response.body()
                    if (apiResponse!!.success){
                        if (response.code() == CODE_SUCCESS || response.code() == 201){
                            mSharedPreferences!!.edit().putString(X_ACCESS_TOKEN, apiResponse.data).apply();
                            mLoginView.socialLoginSuccess()
                        } else
                            mLoginView.socialLoginFailed(apiResponse.msg)
                    } else
                        mLoginView.socialLoginFailed(apiResponse.msg)
                } else
                    mLoginView.socialLoginFailed(null)
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
                if (response.isSuccessful && response.body() != null){
                    val apiResponse = response.body()
                    if (apiResponse!!.success){
                        if (response.code() == CODE_SUCCESS || response.code() == 201){
                            mSharedPreferences!!.edit().putString(X_ACCESS_TOKEN, apiResponse.data).apply();
                            mLoginView.socialLoginSuccess()
                        } else
                            mLoginView.socialLoginFailed(apiResponse.msg)
                    } else
                        mLoginView.socialLoginFailed(apiResponse.msg)
                } else
                    mLoginView.socialLoginFailed(null)
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mLoginView.socialLoginFailed(null)
            }
        })
    }
}