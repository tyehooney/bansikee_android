package com.tomasandfriends.bansikee.src.activities.add_my_plant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.add_my_plant.interfaces.AddMyPlantView
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel

class AddMyPlantViewModel : BaseViewModel(), AddMyPlantView {

    private var plantIdx = 0;

    private val _plantName = MutableLiveData<String>()
    val plantName: LiveData<String> = _plantName

    private val _plantSpecies = MutableLiveData<String>()
    val plantSpecies: LiveData<String> = _plantSpecies

    private val _getPhotoEvent = SingleLiveEvent<Void?>()
    val getPhotoEvent: LiveData<Void?> = _getPhotoEvent

    fun getPlantData(plantIdx: Int, plantName: String, plantSpecies: String){
        this.plantIdx = plantIdx
        this._plantName.value = plantName
        this._plantSpecies.value = plantSpecies
    }

    fun getPhoto(){
        _getPhotoEvent.value = null
    }

    private var mPlantIdx = 0
}