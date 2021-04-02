package com.tomasandfriends.bansikee.src.activities.main.fragment_home

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.main.interfaces.HomeRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.main.interfaces.HomeView
import com.tomasandfriends.bansikee.src.activities.main.models.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(homeView: HomeView) {
    private val mHomeView = homeView

    fun getHomeData(){
        val retrofitInterface = initRetrofit().create(HomeRetrofitInterface::class.java)

        retrofitInterface.getHomeData().enqueue(object: Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mHomeView.getHomeDataSuccess(apiResponse!!.homeData)
                } else {
                    mHomeView.getHomeDataFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                mHomeView.getHomeDataFailed(null)
            }
        })
    }
}