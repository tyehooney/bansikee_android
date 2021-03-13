package com.tomasandfriends.bansikee.src.common.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.common.interfaces.PlantItemView
import com.tomasandfriends.bansikee.src.common.models.PlantData
import com.tomasandfriends.bansikee.src.common.services.PlantItemService

class PlantItemViewModel(plantData: PlantData): BaseViewModel(), PlantItemView {

    private val plantIdx = plantData.plantIdx
    val name = plantData.name
    val species = plantData.species
    val imgUrl = plantData.plantImgUrl
    val info = plantData.info

    private val _like = MutableLiveData(plantData.like)
    val like: LiveData<Boolean> = _like

    private val plantItemService = PlantItemService(this)

    private val _goDetailsEvent = SingleLiveEvent<Int>()
    val goDetailsEvent: LiveData<Int> = _goDetailsEvent

    fun itemClick() {
        _goDetailsEvent.value = plantIdx
    }

    fun itemLikeClick(){
        plantItemService.changePlantLike(plantIdx)
    }

    override fun changePlantLikeSuccess() {
        _like.value = !(like.value!!)
    }

    override fun changePlantLikeFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}