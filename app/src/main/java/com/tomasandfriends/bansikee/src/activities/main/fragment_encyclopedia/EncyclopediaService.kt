package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.main.interfaces.EncyclopediaRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.main.interfaces.EncyclopediaView
import com.tomasandfriends.bansikee.src.common.models.RecommendationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EncyclopediaService(encyclopediaView: EncyclopediaView) {
    private val mEncyclopediaView = encyclopediaView

    fun getSearchedPlants(searchingWord: String, page: Int, sortBy: String){
        val retrofitInterface = initRetrofit().create(EncyclopediaRetrofitInterface::class.java)

        retrofitInterface.getSearchedPlants(searchingWord, page, sortBy).enqueue(object: Callback<RecommendationResponse> {
            override fun onResponse(call: Call<RecommendationResponse>, response: Response<RecommendationResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mEncyclopediaView.getSearchedPlantsSuccess(apiResponse!!.recommendationDataList)
                } else {
                    mEncyclopediaView.getSearchedPlantsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                mEncyclopediaView.getSearchedPlantsFailed(null)
            }
        })
    }
}