package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.common.adapters.PlantItemViewModel
import com.tomasandfriends.bansikee.src.common.interfaces.RecommendationView
import com.tomasandfriends.bansikee.src.common.models.PlantData
import com.tomasandfriends.bansikee.src.common.services.RecommendationService

class EncyclopediaViewModel : BaseViewModel(), RecommendationView {

    private val _recentSearchedPlantItems = MutableLiveData<List<PlantItemViewModel>>()
    val recentSearchedPlantItems: LiveData<List<PlantItemViewModel>> = _recentSearchedPlantItems

    private val _recentSearchLoading = MutableLiveData(true)
    val recentSearchLoading: LiveData<Boolean> = _recentSearchLoading

    private val _recommendedPlantItems = MutableLiveData<List<PlantItemViewModel>>()
    val recommendedPlantItems: LiveData<List<PlantItemViewModel>> = _recommendedPlantItems

    private val _recommendationLoading = MutableLiveData(true)
    val recommendationLoading: LiveData<Boolean> = _recommendationLoading

    private val _goOnboardingEvent = SingleLiveEvent<Void?>()
    val goOnboardingEvent: LiveData<Void?> = _goOnboardingEvent

    private val recommendationService = RecommendationService(this)

    fun getRecommendations(){
        _recommendationLoading.value = true
        recommendationService.getRecommendations()
    }

    fun goOnboardingClick(){
        _goOnboardingEvent.value = null
    }

    override fun getRecommendationsSuccess(recommendations: List<PlantData>) {
        val itemViewModels = ArrayList<PlantItemViewModel>()
        for(plantData in recommendations){
            itemViewModels.add(PlantItemViewModel(plantData))
        }

        _recommendedPlantItems.value = itemViewModels
        _recommendationLoading.value = false
    }

    override fun getRecommendationsFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}