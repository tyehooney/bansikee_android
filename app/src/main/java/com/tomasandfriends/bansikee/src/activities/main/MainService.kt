package com.tomasandfriends.bansikee.src.activities.main

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MainRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MainView
import com.tomasandfriends.bansikee.src.common.models.BooleanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService(mainView: MainView) {
    private val mMainView = mainView

    fun isOnboarded(){
        val retrofitInterface = initRetrofit().create(MainRetrofitInterface::class.java)

        retrofitInterface.isOnboarded().enqueue(object: Callback<BooleanResponse> {
            override fun onResponse(call: Call<BooleanResponse>, response: Response<BooleanResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSharedPreferences!!.edit().putBoolean("onboarded", apiResponse!!.data).apply()
                    mMainView.isOnboardedSuccess(apiResponse!!.data)
                } else {
                    mMainView.isOnboardedFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                mMainView.isOnboardedFailed(null)
            }
        })
    }
}