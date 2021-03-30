package com.tomasandfriends.bansikee.src.activities.liking_plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.common.adapters.PlantItemViewModel
import com.tomasandfriends.bansikee.src.common.interfaces.PlantsView
import com.tomasandfriends.bansikee.src.common.models.PlantData
import com.tomasandfriends.bansikee.src.common.services.PlantsService

class LikingPlantsViewModel: BaseViewModel(), PlantsView {

    private val _likingPlantItems = MutableLiveData<List<PlantItemViewModel>>()
    val likingPlantItems: LiveData<List<PlantItemViewModel>> = _likingPlantItems

    private val likingPlantsService = PlantsService(this)

    fun getLikingPlants(){
        _loading.value = true
        likingPlantsService.getLikingPlants()
    }

    override fun getPlantsSuccess(plants: List<PlantData>) {
        val results = ArrayList<PlantItemViewModel>()

        for (plantData in plants){
            results.add(PlantItemViewModel(plantData))
        }

        _likingPlantItems.value = results
        _loading.value = false
    }

    override fun getPlantsFailed(msg: String?) {
        _loading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}