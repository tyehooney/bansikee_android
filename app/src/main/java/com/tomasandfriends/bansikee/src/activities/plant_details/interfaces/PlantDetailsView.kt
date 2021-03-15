package com.tomasandfriends.bansikee.src.activities.plant_details.interfaces

import com.tomasandfriends.bansikee.src.activities.plant_details.models.PlantDetailsData

interface PlantDetailsView {

    fun getPlantDetailsSuccess(plantDetailsData: PlantDetailsData)
    fun getPlantDetailsFailed(msg: String?)

}