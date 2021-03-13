package com.tomasandfriends.bansikee.src.common.services

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.common.interfaces.CommonRetrofitInterface
import com.tomasandfriends.bansikee.src.common.interfaces.PlantItemView
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantItemService(plantItemView: PlantItemView) {
    private val mPlantItemView = plantItemView

    fun changePlantLike(plantIdx: Int){
        val retrofitInterface = initRetrofit().create(CommonRetrofitInterface::class.java)

        retrofitInterface.changePlantLike(plantIdx).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    mPlantItemView.changePlantLikeSuccess()
                } else {
                    mPlantItemView.changePlantLikeFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mPlantItemView.changePlantLikeFailed(null)
            }
        })
    }
}