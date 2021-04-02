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

    val plantIdx = plantData.plantIdx
    val name = plantData.name
    val species = plantData.species
    val imgUrl = plantData.plantImgUrl
    val info = plantData.info

    var deletable = false

    private val _deleteShowing = MutableLiveData(false)
    val deleteShowing: LiveData<Boolean> = _deleteShowing

    fun setDeleteShowing(b: Boolean){
        _deleteShowing.value = b
    }

    private val _deleteEvent = SingleLiveEvent<Int>()
    val deleteEvent: LiveData<Int> = _deleteEvent

    fun deleteClick() {
        _deleteEvent.value = plantIdx
    }

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

    override fun changePlantLikeSuccess(msg: String) {
        _like.value = !(like.value!!)
    }

    override fun changePlantLikeFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}