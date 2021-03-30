package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.main.interfaces.EncyclopediaRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.main.interfaces.EncyclopediaView
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.common.models.PlantsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EncyclopediaService(encyclopediaView: EncyclopediaView) {
    private val mEncyclopediaView = encyclopediaView

    fun getSearchedPlants(searchingWord: String, page: Int, sortBy: String){
        val retrofitInterface = initRetrofit().create(EncyclopediaRetrofitInterface::class.java)

        retrofitInterface.getSearchedPlants(searchingWord, page, sortBy).enqueue(object: Callback<PlantsResponse> {
            override fun onResponse(call: Call<PlantsResponse>, response: Response<PlantsResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mEncyclopediaView.getSearchedPlantsSuccess(page, apiResponse!!.plantDataList)
                } else {
                    mEncyclopediaView.getSearchedPlantsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                mEncyclopediaView.getSearchedPlantsFailed(null)
            }
        })
    }

    fun getRecentlySearchedPlants(){
        val retrofitInterface = initRetrofit().create(EncyclopediaRetrofitInterface::class.java)

        retrofitInterface.getRecentlySearchedPlants().enqueue(object: Callback<PlantsResponse> {
            override fun onResponse(call: Call<PlantsResponse>, response: Response<PlantsResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mEncyclopediaView.getRecentlySearchedPlantsSuccess(apiResponse!!.plantDataList)
                } else {
                    mEncyclopediaView.getRecentlySearchedPlantsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                mEncyclopediaView.getRecentlySearchedPlantsFailed(null)
            }
        })
    }

    fun deleteAllSearchedPlants(){
        val retrofitInterface = initRetrofit().create(EncyclopediaRetrofitInterface::class.java)

        retrofitInterface.deleteAllSearchedPlants().enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mEncyclopediaView.deleteSearchedPlantSuccess(apiResponse!!.detail)
                } else {
                    mEncyclopediaView.deleteSearchedPlantFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mEncyclopediaView.deleteSearchedPlantFailed(null)
            }
        })
    }

    fun deleteSearchedPlant(plantIdx: Int){
        val retrofitInterface = initRetrofit().create(EncyclopediaRetrofitInterface::class.java)

        retrofitInterface.deleteSearchedPlant(plantIdx).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mEncyclopediaView.deleteSearchedPlantSuccess(apiResponse!!.detail)
                } else {
                    mEncyclopediaView.deleteSearchedPlantFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mEncyclopediaView.deleteSearchedPlantFailed(null)
            }
        })
    }
}