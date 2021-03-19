package com.tomasandfriends.bansikee.src.activities.plant_details

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.plant_details.interfaces.PlantDetailsRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.plant_details.interfaces.PlantDetailsView
import com.tomasandfriends.bansikee.src.activities.plant_details.models.PlantDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantDetailsService(plantDetailsView: PlantDetailsView) {
    private val mPlantDetailsView = plantDetailsView

    fun getPlantDetails(plantIdx: Int, status: String?){
        val retrofitInterface = initRetrofit().create(PlantDetailsRetrofitInterface::class.java)

        retrofitInterface.getPlantDetails(plantIdx, status).enqueue(object: Callback<PlantDetailsResponse> {
            override fun onResponse(call: Call<PlantDetailsResponse>, response: Response<PlantDetailsResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mPlantDetailsView.getPlantDetailsSuccess(apiResponse!!.plantDetailsData)
                } else {
                    mPlantDetailsView.getPlantDetailsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<PlantDetailsResponse>, t: Throwable) {
                mPlantDetailsView.getPlantDetailsFailed(null)
            }
        })
    }
}