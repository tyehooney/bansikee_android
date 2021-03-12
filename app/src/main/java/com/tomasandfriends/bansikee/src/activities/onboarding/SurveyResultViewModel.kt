package com.tomasandfriends.bansikee.src.activities.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.common.models.PlantData
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.SurveyResultView
import com.tomasandfriends.bansikee.src.common.adapters.PlantItemViewModel

class SurveyResultViewModel: BaseViewModel(), SurveyResultView {

    private val _surveyResultItems = MutableLiveData<List<PlantItemViewModel>>()
    val surveyResultItems: LiveData<List<PlantItemViewModel>> = _surveyResultItems

    private val surveyResultService = SurveyResultService(this)

    val onBoarded = mSharedPreferences!!.getBoolean("onboarded", false)

    init {
        surveyResultService.getSurveyResults()
    }

    override fun getSurveyResultsSuccess(surveyResults: List<PlantData>) {
        val itemViewModels = ArrayList<PlantItemViewModel>()
        for(plantData in surveyResults){
            itemViewModels.add(PlantItemViewModel(plantData))
        }

        _surveyResultItems.value = itemViewModels
    }

    override fun getSurveyResultsFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}