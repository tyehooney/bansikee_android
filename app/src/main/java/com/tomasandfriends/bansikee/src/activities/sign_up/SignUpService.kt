package com.tomasandfriends.bansikee.src.activities.sign_up

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.sign_up.interfaces.SignUpRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.sign_up.interfaces.SignUpView
import com.tomasandfriends.bansikee.src.activities.sign_up.models.SignUpBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpService(signUpView: SignUpView) {
    private val mSignUpView = signUpView

    fun signUp(signUpBody: SignUpBody){
        val retroInterface = ApplicationClass.initRetrofit().create(SignUpRetrofitInterface::class.java)

        retroInterface.signUp(signUpBody).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSignUpView.signUpSuccess(apiResponse!!.detail)
                } else {
                    mSignUpView.signUpFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mSignUpView.signUpFailed(null)
            }
        })
    }
}