package com.tomasandfriends.bansikee.src.activities.diary

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.diary.interfaces.DiaryRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.diary.interfaces.DiaryView
import com.tomasandfriends.bansikee.src.activities.diary.models.AddDiaryBody
import com.tomasandfriends.bansikee.src.activities.diary.models.DiaryDetailsResponse
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryService(diaryView: DiaryView) {
    private val mDiaryView = diaryView

    fun getDiaryDetails(diaryIdx: Int){
        val retrofitInterface = initRetrofit().create(DiaryRetrofitInterface::class.java)

        retrofitInterface.getDiaryDetails(diaryIdx).enqueue(object: Callback<DiaryDetailsResponse> {
            override fun onResponse(call: Call<DiaryDetailsResponse>, response: Response<DiaryDetailsResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mDiaryView.getDiaryDetailsSuccess(apiResponse!!.diaryData)
                } else {
                    mDiaryView.getDiaryDetailsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DiaryDetailsResponse>, t: Throwable) {
                mDiaryView.getDiaryDetailsFailed(null)
            }
        })
    }

    fun addDiary(addDiaryBody: AddDiaryBody){
        val retrofitInterface = initRetrofit().create(DiaryRetrofitInterface::class.java)

        retrofitInterface.addDiary(addDiaryBody).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mDiaryView.addDiarySuccess(apiResponse!!.detail)
                } else {
                    mDiaryView.addDiaryFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                mDiaryView.addDiaryFailed(null)
            }
        })
    }
}