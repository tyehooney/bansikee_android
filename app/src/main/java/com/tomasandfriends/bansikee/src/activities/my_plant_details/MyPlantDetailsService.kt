package com.tomasandfriends.bansikee.src.activities.my_plant_details

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.my_plant_details.interfaces.MyPlantDetailsRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.my_plant_details.interfaces.MyPlantDetailsView
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.DiaryListResponse
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.MyPlantDetailsResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlantDetailsService(myPlantDetailsView: MyPlantDetailsView) {

    private val mMyPlantDetailsView = myPlantDetailsView

    fun getMyPlantDetails(myPlantIdx: Int){
        val retrofitInterface = initRetrofit().create(MyPlantDetailsRetrofitInterface::class.java)

        retrofitInterface.getMyPlantDetails(myPlantIdx).enqueue(object: Callback<MyPlantDetailsResponse> {
            override fun onResponse(call: Call<MyPlantDetailsResponse>, response: Response<MyPlantDetailsResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mMyPlantDetailsView.getMyPlantDetailsSuccess(apiResponse!!.myPlantDetailsData)
                } else {
                    mMyPlantDetailsView.getMyPlantDetailsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<MyPlantDetailsResponse>, t: Throwable) {
                mMyPlantDetailsView.getMyPlantDetailsFailed(null)
            }
        })
    }

    fun getDiaryList(myPlantIdx: Int){
        val retrofitInterface = initRetrofit().create(MyPlantDetailsRetrofitInterface::class.java)

        retrofitInterface.getDiaryList(myPlantIdx).enqueue(object: Callback<DiaryListResponse> {
            override fun onResponse(call: Call<DiaryListResponse>, response: Response<DiaryListResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mMyPlantDetailsView.getDiaryListSuccess(apiResponse!!.diaryList)
                } else {
                    mMyPlantDetailsView.getDiaryListFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DiaryListResponse>, t: Throwable) {
                mMyPlantDetailsView.getDiaryListFailed(null)
            }
        })
    }

    fun deleteDiary(diaryIdx: Int){
        val retrofitInterface = initRetrofit().create(MyPlantDetailsRetrofitInterface::class.java)

        retrofitInterface.deleteDiary(diaryIdx).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mMyPlantDetailsView.deleteDiarySuccess(apiResponse!!.detail)
                } else {
                    mMyPlantDetailsView.deleteDiaryFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mMyPlantDetailsView.deleteDiaryFailed(null)
            }
        })
    }
}