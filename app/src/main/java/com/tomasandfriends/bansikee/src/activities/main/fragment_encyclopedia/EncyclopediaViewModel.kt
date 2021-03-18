package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import android.os.Handler
import android.os.Looper
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
    var lastSearchingWord = ""
    private var searchPage = 1
    private val _forRefreshing = MutableLiveData(true)
    val forRefreshing: LiveData<Boolean> = _forRefreshing

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
        lastSearchingWord = searchingWord.value!!
        searchPage = 1
        _searchPlantsEvent.value = null
    }

    fun searchPlants(){
        _searchingLoading.value = true
        _forRefreshing.value = true
        for(i in 1..searchPage)
            encyclopediaService.getSearchedPlants(lastSearchingWord, i, "popularity")
    }

    fun onLoadMore(){
        Handler(Looper.myLooper()!!).postDelayed({
            _forRefreshing.value = false
            encyclopediaService.getSearchedPlants(lastSearchingWord, ++searchPage, "popularity")
        }, 500)
    }

    override fun getSearchedPlantsSuccess(loadedPage: Int, searchedPlants: List<PlantData>) {
        if (loadedPage == 1)
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