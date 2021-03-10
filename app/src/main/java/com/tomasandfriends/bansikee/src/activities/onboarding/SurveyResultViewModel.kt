package com.tomasandfriends.bansikee.src.activities.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.SurveyResultView
import com.tomasandfriends.bansikee.src.activities.onboarding.models.RecommendationData

class SurveyResultViewModel: BaseViewModel(), SurveyResultView {

    private val _surveyResults = MutableLiveData<List<RecommendationData>>()
    val surveyResults: LiveData<List<RecommendationData>> = _surveyResults

    private val surveyResultService = SurveyResultService(this)

    init {
        surveyResultService.getSurveyResults()
    }

    override fun getSurveyResultsSuccess(surveyResults: List<RecommendationData>) {}

    override fun getSurveyResultsFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

}