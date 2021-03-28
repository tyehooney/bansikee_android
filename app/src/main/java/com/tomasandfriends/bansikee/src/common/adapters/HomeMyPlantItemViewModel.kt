package com.tomasandfriends.bansikee.src.common.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.main.models.HomeMyPlantData

class HomeMyPlantItemViewModel(homeMyPlant: HomeMyPlantData): ViewModel() {

    private val myPlantIdx = homeMyPlant.myPlantIdx
    val myPlantName = homeMyPlant.nickname
    val myPlantImg = homeMyPlant.imgUrl
    val myPlantAge = homeMyPlant.age
    val myPlantSpecies = homeMyPlant.species
    val myPlantWaterCycle = homeMyPlant.waterCycle
    val myPlantLastWateredDay = homeMyPlant.lastWateredDay
    val myPlantWateredDayFrom = homeMyPlant.wateredDayFrom
    val myPlantWateredDayTo = homeMyPlant.wateredDayTo
    val didWriteDiaryToday = homeMyPlant.didWriteDiaryToday

    private val _itemClickEvent = SingleLiveEvent<Int>()
    val itemClickEvent: LiveData<Int> = _itemClickEvent

    fun itemClick(){
        _itemClickEvent.value = myPlantIdx
    }

}