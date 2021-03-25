package com.tomasandfriends.bansikee.src.common.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.main.models.MyPlantData

class MyPlantItemViewModel(plantData: MyPlantData): ViewModel() {

    private val myPlantIdx = plantData.plantIdx
    val name = plantData.nickname
    val plantName = plantData.plantName
    val imgUrl = plantData.imgUrl
    val contents = plantData.contents

    private val _deleteShowing = MutableLiveData(false)
    val deleteShowing: LiveData<Boolean> = _deleteShowing

    fun setDeleteShowing(b: Boolean){
        _deleteShowing.value = b
    }

    private val _goDetailsEvent = SingleLiveEvent<Int>()
    val goDetailsEvent: LiveData<Int> = _goDetailsEvent

    fun itemClick() {
        _goDetailsEvent.value = myPlantIdx
    }

    private val _deleteEvent = SingleLiveEvent<Int>()
    val deleteEvent: LiveData<Int> = _deleteEvent

    fun deleteClick() {
        _deleteEvent.value = myPlantIdx
    }
}