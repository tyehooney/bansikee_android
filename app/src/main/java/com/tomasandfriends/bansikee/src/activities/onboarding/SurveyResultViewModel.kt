package com.tomasandfriends.bansikee.src.activities.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.common.models.PlantData
import com.tomasandfriends.bansikee.src.common.interfaces.RecommendationView
import com.tomasandfriends.bansikee.src.common.adapters.PlantItemViewModel
import com.tomasandfriends.bansikee.src.common.services.RecommendationService

class SurveyResultViewModel: BaseViewModel(), RecommendationView {

    private val _surveyResultItems = MutableLiveData<List<PlantItemViewModel>>()
    val surveyResultItems: LiveData<List<PlantItemViewModel>> = _surveyResultItems

    private val surveyResultService = RecommendationService(this)

    val onBoarded = mSharedPreferences!!.getBoolean("onboarded", false)

    init {
        getResult()
    }

    fun getResult(){
        surveyResultService.getRecommendations()
    }

    override fun getRecommendationsSuccess(recommendations: List<PlantData>) {
        val itemViewModels = ArrayList<PlantItemViewModel>()
        for(plantData in recommendations){
            itemViewModels.add(PlantItemViewModel(plantData))
        }

        _surveyResultItems.value = itemViewModels
    }

    override fun getRecommendationsFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}