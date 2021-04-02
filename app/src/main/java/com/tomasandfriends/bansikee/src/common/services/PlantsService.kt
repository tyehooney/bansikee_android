package com.tomasandfriends.bansikee.src.common.services

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.common.interfaces.PlantsView
import com.tomasandfriends.bansikee.src.common.models.PlantsResponse
import com.tomasandfriends.bansikee.src.common.interfaces.CommonRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantsService(plantsView: PlantsView) {
    private val mPlantsView = plantsView

    fun getRecommendations(){
        val retrofitInterface = ApplicationClass.initRetrofit().create(CommonRetrofitInterface::class.java)

        retrofitInterface.getRecommendations().enqueue(object: Callback<PlantsResponse> {
            override fun onResponse(call: Call<PlantsResponse>, response: Response<PlantsResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mPlantsView.getPlantsSuccess(apiResponse!!.plantDataList)
                } else {
                    mPlantsView.getPlantsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                mPlantsView.getPlantsFailed(null)
            }
        })
    }

    fun getLikingPlants(){
        val retrofitInterface = ApplicationClass.initRetrofit().create(CommonRetrofitInterface::class.java)

        retrofitInterface.getLikingPlants().enqueue(object: Callback<PlantsResponse> {
            override fun onResponse(
                call: Call<PlantsResponse>,
                response: Response<PlantsResponse>
            ) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mPlantsView.getPlantsSuccess(apiResponse!!.plantDataList)
                } else {
                    mPlantsView.getPlantsFailed(
                        if(response.body() == null)
                            ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                        else
                            response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                mPlantsView.getPlantsFailed(null)
            }
        })
    }
}