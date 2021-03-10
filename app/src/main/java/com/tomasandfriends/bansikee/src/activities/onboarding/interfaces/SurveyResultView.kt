package com.tomasandfriends.bansikee.src.activities.onboarding.interfaces

import com.tomasandfriends.bansikee.src.activities.onboarding.models.RecommendationData

interface SurveyResultView {
    fun getSurveyResultsSuccess(surveyResults: List<RecommendationData>)
    fun getSurveyResultsFailed(msg : String?)
}