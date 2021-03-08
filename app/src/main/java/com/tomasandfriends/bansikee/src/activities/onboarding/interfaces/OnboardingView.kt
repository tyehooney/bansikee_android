package com.tomasandfriends.bansikee.src.activities.onboarding.interfaces

import com.tomasandfriends.bansikee.src.activities.onboarding.models.SurveyData

interface OnboardingView {
    fun getSurveySuccess(surveyList: List<SurveyData>)
    fun getSurveyFailed(msg : String?)
}