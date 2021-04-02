package com.tomasandfriends.bansikee.src.activities.main.fragment_my_garden

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MyGardenRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MyGardenView
import com.tomasandfriends.bansikee.src.activities.main.models.MyPlantsResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyGardenService(myGardenView: MyGardenView) {
    private val mMyGardenView = myGardenView

    fun getMyPlants(){
        val retrofitInterface = initRetrofit().create(MyGardenRetrofitInterface::class.java)

        retrofitInterface.getMyPlants().enqueue(object: Callback<MyPlantsResponse> {
            override fun onResponse(call: Call<MyPlantsResponse>, response: Response<MyPlantsResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mMyGardenView.getMyPlantsSuccess(apiResponse!!.myPlantDataList)
                } else {
                    mMyGardenView.getMyPlantsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<MyPlantsResponse>, t: Throwable) {
                mMyGardenView.getMyPlantsFailed(null)
            }
        })
    }

    fun deleteMyPlant(myPlantIdx: Int){
        val retrofitInterface = initRetrofit().create(MyGardenRetrofitInterface::class.java)

        retrofitInterface.deleteMyPlant(myPlantIdx).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mMyGardenView.deleteMyPlantSuccess(apiResponse!!.detail)
                } else {
                    mMyGardenView.deleteMyPlantFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mMyGardenView.deleteMyPlantFailed(null)
            }
        })
    }
}