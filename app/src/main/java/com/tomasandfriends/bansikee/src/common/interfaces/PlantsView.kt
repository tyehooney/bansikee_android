package com.tomasandfriends.bansikee.src.common.interfaces

import com.tomasandfriends.bansikee.src.common.models.PlantData

interface PlantsView {

    fun getPlantsSuccess(plants: List<PlantData>)
    fun getPlantsFailed(msg : String?)

}