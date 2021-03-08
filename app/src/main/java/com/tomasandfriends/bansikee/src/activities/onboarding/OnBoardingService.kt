package com.tomasandfriends.bansikee.src.activities.onboarding

import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.getErrorResponse
import com.tomasandfriends.bansikee.ApplicationClass.Companion.initRetrofit
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.OnboardingRetrofitInterface
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.OnboardingView
import com.tomasandfriends.bansikee.src.activities.onboarding.models.GetSurveyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnBoardingService(onboardingView: OnboardingView) {
    private val mOnboardingView = onboardingView

    fun getSurveyList(){
        val retrofitInterface = initRetrofit().create(OnboardingRetrofitInterface::class.java)

        retrofitInterface.getSurvey().enqueue(object: Callback<GetSurveyResponse> {
            override fun onResponse(call: Call<GetSurveyResponse>, response: Response<GetSurveyResponse>) {
                if(response.code() == ApplicationClass.CODE_SUCCESS){
                    val apiResponse = response.body()
                    mOnboardingView.getSurveySuccess(apiResponse!!.surveyDataList)
                } else {
                    mOnboardingView.getSurveyFailed(
                            if(response.body() == null)
                                getErrorResponse(response.errorBody()!!)!!.detail
                            else
                                response.body()!!.detail
                    )
                }
            }

            override fun onFailure(call: Call<GetSurveyResponse>, t: Throwable) {
                mOnboardingView.getSurveyFailed(null)
            }
        })
    }
}