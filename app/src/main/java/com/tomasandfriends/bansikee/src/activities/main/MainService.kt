package com.tomasandfriends.bansikee.src.activities.main

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MainRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MainView
import com.tomasandfriends.bansikee.src.activities.main.models.IsOnboardedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService(mainView: MainView) {
    private val mMainView = mainView

    fun isOnboarded(){
        val retrofitInterface = initRetrofit().create(MainRetrofitInterface::class.java)

        retrofitInterface.isOnboarded().enqueue(object: Callback<IsOnboardedResponse> {
            override fun onResponse(call: Call<IsOnboardedResponse>, response: Response<IsOnboardedResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mMainView.isOnboardedSuccess(apiResponse!!.onboarded)
                } else {
                    mMainView.isOnboardedFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<IsOnboardedResponse>, t: Throwable) {
                mMainView.isOnboardedFailed(null)
            }
        })
    }
}