package com.tomasandfriends.bansikee.src.activities.onboarding.interfaces

import com.tomasandfriends.bansikee.src.common.models.PlantData

interface SurveyResultView {
    fun getSurveyResultsSuccess(surveyResults: List<PlantData>)
    fun getSurveyResultsFailed(msg : String?)
}