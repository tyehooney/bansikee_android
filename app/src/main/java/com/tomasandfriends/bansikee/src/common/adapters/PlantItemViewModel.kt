package com.tomasandfriends.bansikee.src.common.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.common.models.PlantData

class PlantItemViewModel(plantData: PlantData): ViewModel() {

    val plantIdx = plantData.plantIdx
    val name = plantData.name
    val species = plantData.species
    val imgUrl = plantData.plantImgUrl
    val info = plantData.info

    private val _like = MutableLiveData(plantData.like)
    val like: LiveData<Boolean> = _like

    private val _goDetailsEvent = SingleLiveEvent<Int>()
    val goDetailsEvent: LiveData<Int> = _goDetailsEvent
}