package com.tomasandfriends.bansikee.src.activities.add_my_plant

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.add_my_plant.interfaces.AddMyPlantRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.add_my_plant.interfaces.AddMyPlantView
import com.tomasandfriends.bansikee.src.activities.add_my_plant.models.AddPlantBody
import com.tomasandfriends.bansikee.src.activities.add_my_plant.models.EditMyPlantBody
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMyPlantService(addMyPlantView: AddMyPlantView) {
    private val mAddMyPlantView = addMyPlantView

    fun addToMyPlants(addPlantBody: AddPlantBody) {
        val retrofitInterface = initRetrofit().create(AddMyPlantRetrofitInterface::class.java)

        retrofitInterface.addToMyPlants(addPlantBody).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mAddMyPlantView.addToMyPlantsSuccess(apiResponse!!.detail)
                } else {
                    mAddMyPlantView.addToMyPlantsFailed(
                        if(response.body() == null)
                            ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                        else
                            response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mAddMyPlantView.addToMyPlantsFailed(null)
            }
        })
    }

    fun editMyPlant(editMyPlantBody: EditMyPlantBody) {
        val retrofitInterface = initRetrofit().create(AddMyPlantRetrofitInterface::class.java)

        retrofitInterface.editMyPlant(editMyPlantBody).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
            ) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mAddMyPlantView.addToMyPlantsSuccess(apiResponse!!.detail)
                } else {
                    mAddMyPlantView.addToMyPlantsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mAddMyPlantView.addToMyPlantsFailed(null)
            }
        })
    }
}