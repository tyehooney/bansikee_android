package com.tomasandfriends.bansikee.src.common.services

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.common.interfaces.RecommendationView
import com.tomasandfriends.bansikee.src.common.models.RecommendationResponse
import com.tomasandfriends.bansikee.src.common.interfaces.CommonRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationService(recommendationView: RecommendationView) {
    private val mRecommendationView = recommendationView

    fun getRecommendations(){
        val retrofitInterface = ApplicationClass.initRetrofit().create(CommonRetrofitInterface::class.java)

        retrofitInterface.getRecommendations().enqueue(object: Callback<RecommendationResponse> {
            override fun onResponse(call: Call<RecommendationResponse>, response: Response<RecommendationResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mRecommendationView.getRecommendationsSuccess(apiResponse!!.recommendationDataList)
                } else {
                    mRecommendationView.getRecommendationsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                mRecommendationView.getRecommendationsFailed(null)
            }
        })
    }
}