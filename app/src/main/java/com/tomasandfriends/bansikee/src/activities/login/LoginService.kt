package com.tomasandfriends.bansikee.src.activities.login

import com.tomasandfriends.bansikee.ApplicationClass.Companion.CODE_SUCCESS
import com.tomasandfriends.bansikee.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.tomasandfriends.bansikee.ApplicationClass.Companion.getErrorResponse
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.login.interfaces.LoginRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.login.interfaces.LoginView
import com.tomasandfriends.bansikee.src.activities.login.models.AccessObject
import com.tomasandfriends.bansikee.src.activities.login.models.LoginBody
import com.tomasandfriends.bansikee.src.activities.login.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(loginView: LoginView) {
    private val mLoginView = loginView

    fun googleLogin(idToken: String){
        val retroInterface = initRetrofit().create(LoginRetrofitInterface::class.java)

        retroInterface.googleLogin(AccessObject(idToken)).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.code() == CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSharedPreferences!!.edit().putString(X_ACCESS_TOKEN, apiResponse!!.data).apply()
                    mLoginView.loginSuccess()
                } else {
                    mLoginView.loginFailed(
                            if(response.body() == null)
                                getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                mLoginView.loginFailed(null)
            }
        })
    }

    fun kakaoLogin(accessToken: String){
        val retroInterface = initRetrofit().create(LoginRetrofitInterface::class.java)

        retroInterface.kakaoLogin(AccessObject(accessToken)).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.code() == CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSharedPreferences!!.edit().putString(X_ACCESS_TOKEN, apiResponse!!.data).apply()
                    mLoginView.loginSuccess()
                } else {
                    mLoginView.loginFailed(
                            if(response.body() == null)
                                getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                mLoginView.loginFailed(null)
            }
        })
    }

    fun basicLogin(loginBody: LoginBody){
        val retroInterface = initRetrofit().create(LoginRetrofitInterface::class.java)

        retroInterface.basicLogin(loginBody).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.code() == CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSharedPreferences!!.edit().putString(X_ACCESS_TOKEN, apiResponse!!.data).apply()
                    mLoginView.loginSuccess()
                } else {
                    mLoginView.loginFailed(
                            if(response.body() == null)
                                getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                mLoginView.loginFailed(null)
            }
        })
    }
}