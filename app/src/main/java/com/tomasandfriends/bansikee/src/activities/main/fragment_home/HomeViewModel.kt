package com.tomasandfriends.bansikee.src.activities.main.fragment_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.USER_IMG
import com.tomasandfriends.bansikee.ApplicationClass.Companion.USER_NAME
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.main.interfaces.HomeView
import com.tomasandfriends.bansikee.src.activities.main.models.HomeData
import com.tomasandfriends.bansikee.src.common.adapters.HomeMyPlantItemViewModel

class HomeViewModel : BaseViewModel(), HomeView {

    private val _homeData = MutableLiveData<HomeData>()
    val homeData: LiveData<HomeData> = _homeData

    private val _homeMyPlantItems = MutableLiveData<List<HomeMyPlantItemViewModel>>()
    val homeMyPlantItems: LiveData<List<HomeMyPlantItemViewModel>> = _homeMyPlantItems

    private val _goEncyclopediaEvent = SingleLiveEvent<Void?>()
    val goEncyclopediaEvent: LiveData<Void?> = _goEncyclopediaEvent

    private val _goPlantDetailsEvent = SingleLiveEvent<Int>()
    val goPlantDetailsEvent: LiveData<Int> = _goPlantDetailsEvent

    private val _goOnboardingEvent = SingleLiveEvent<Void?>()
    val goOnboardingEvent: LiveData<Void?> = _goOnboardingEvent

    private val _goMyGardenEvent = SingleLiveEvent<Void?>()
    val goMyGardenEvent: LiveData<Void?> = _goMyGardenEvent

    private val homeService = HomeService(this)

    fun callHomeData(){
        _loading.value = true
        homeService.getHomeData()
    }

    fun goEncyclopediaClick(){
        _goEncyclopediaEvent.value = null
    }

    fun goPlantDetails(){
        _goPlantDetailsEvent.value = homeData.value!!.recommendedPlantIdx
    }

    fun goOnboardingClick(){
        _goOnboardingEvent.value = null
    }

    fun goMyGardenClick(){
        _goMyGardenEvent.value = null
    }

    override fun getHomeDataSuccess(homeData: HomeData) {
        _homeData.value = homeData

        val myPlantViewModels = ArrayList<HomeMyPlantItemViewModel>()
        val myPlants = homeData.myPlants
        for(i in 0 until myPlants.size){
            myPlantViewModels.add(HomeMyPlantItemViewModel(myPlants[i]))
        }

        val editor = mSharedPreferences!!.edit()
        editor.putString(USER_NAME, homeData.userName)
        editor.putString(USER_IMG, homeData.userImg).apply()

        _homeMyPlantItems.value = myPlantViewModels

        _loading.value = false
    }

    override fun getHomeDataFailed(msg: String?) {
        _loading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}