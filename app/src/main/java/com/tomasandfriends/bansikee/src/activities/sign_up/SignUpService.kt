package com.tomasandfriends.bansikee.src.activities.sign_up

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.activities.sign_up.interfaces.SignUpRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.sign_up.interfaces.SignUpView
import com.tomasandfriends.bansikee.src.activities.sign_up.models.NicknameBody
import com.tomasandfriends.bansikee.src.activities.sign_up.models.SignUpBody
import com.tomasandfriends.bansikee.src.common.models.BooleanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpService(signUpView: SignUpView) {
    private val mSignUpView = signUpView

    fun checkNickname(nickname: String){
        val retroInterface = ApplicationClass.initRetrofit().create(SignUpRetrofitInterface::class.java)

        retroInterface.checkNickname(NicknameBody(nickname)).enqueue(object: Callback<BooleanResponse>{
            override fun onResponse(call: Call<BooleanResponse>, response: Response<BooleanResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSignUpView.checkNicknameSuccess(apiResponse!!.data)
                } else {
                    mSignUpView.checkNicknameFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail)
                }
            }

            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                mSignUpView.checkNicknameFailed(null)
            }
        })
    }

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