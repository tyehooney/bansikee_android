package com.tomasandfriends.bansikee.src.activities.onboarding

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.OnboardingRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.SurveyResultView
import com.tomasandfriends.bansikee.src.activities.onboarding.models.RecommendationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyResultService(surveyResultView: SurveyResultView) {
    private val mSurveyResultView = surveyResultView

    fun getSurveyResults(){
        val retrofitInterface = ApplicationClass.initRetrofit().create(OnboardingRetrofitInterface::class.java)

        retrofitInterface.getRecommendations().enqueue(object: Callback<RecommendationResponse> {
            override fun onResponse(call: Call<RecommendationResponse>, response: Response<RecommendationResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mSurveyResultView.getSurveyResultsSuccess(apiResponse!!.surveyResultDataList)
                } else {
                    mSurveyResultView.getSurveyResultsFailed(
                            if(response.body() == null)
                                ApplicationClass.getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                mSurveyResultView.getSurveyResultsFailed(null)
            }
        })
    }
}