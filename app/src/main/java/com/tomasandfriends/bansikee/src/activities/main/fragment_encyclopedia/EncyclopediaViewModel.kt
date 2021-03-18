package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.main.interfaces.EncyclopediaView
import com.tomasandfriends.bansikee.src.common.adapters.PlantItemViewModel
import com.tomasandfriends.bansikee.src.common.interfaces.RecommendationView
import com.tomasandfriends.bansikee.src.common.models.PlantData
import com.tomasandfriends.bansikee.src.common.services.RecommendationService

class EncyclopediaViewModel : BaseViewModel(), RecommendationView, EncyclopediaView {

    val searchingWord = MutableLiveData("")

    private val _recentSearchedPlantItems = MutableLiveData<List<PlantItemViewModel>>()
    val recentSearchedPlantItems: LiveData<List<PlantItemViewModel>> = _recentSearchedPlantItems

    private val _recentSearchLoading = MutableLiveData(true)
    val recentSearchLoading: LiveData<Boolean> = _recentSearchLoading

    private val _recommendedPlantItems = MutableLiveData<List<PlantItemViewModel>>()
    val recommendedPlantItems: LiveData<List<PlantItemViewModel>> = _recommendedPlantItems

    private val _recommendationLoading = MutableLiveData(true)
    val recommendationLoading: LiveData<Boolean> = _recommendationLoading

    private val _searchedPlantItems = MutableLiveData<List<PlantItemViewModel>>()
    val searchedPlantItems: LiveData<List<PlantItemViewModel>> = _searchedPlantItems

    private val _searchingLoading = MutableLiveData(true)
    val searchingLoading: LiveData<Boolean> = _searchingLoading

    var searchPage = 1

    private val _searchPlantsEvent = SingleLiveEvent<Void?>()
    val searchPlantsEvent: LiveData<Void?> = _searchPlantsEvent

    private val _goOnboardingEvent = SingleLiveEvent<Void?>()
    val goOnboardingEvent: LiveData<Void?> = _goOnboardingEvent

    private val recommendationService = RecommendationService(this)
    private val encyclopediaService = EncyclopediaService(this)

    fun goOnboardingClick(){
        _goOnboardingEvent.value = null
    }

    fun getRecommendations(){
        _recommendationLoading.value = true
        recommendationService.getRecommendations()
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
        _recommendationLoading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

    fun searchPlantsClick(){
        _searchPlantsEvent.value = null
    }

    fun searchPlants(){
        _searchingLoading.value = true
        encyclopediaService.getSearchedPlants(searchingWord.value!!, searchPage, "popularity")
    }

    override fun getSearchedPlantsSuccess(searchedPlants: List<PlantData>) {
        if (searchPage == 1)
            _searchedPlantItems.value = ArrayList()

        val results = ArrayList<PlantItemViewModel>()
        results.addAll(_searchedPlantItems.value!!)

        for(plantData in searchedPlants){
            results.add(PlantItemViewModel(plantData))
        }

        _searchedPlantItems.value = results
        _searchingLoading.value = false
        _clearInput.value = null
    }

    override fun getSearchedPlantsFailed(msg: String?) {
        _searchingLoading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}