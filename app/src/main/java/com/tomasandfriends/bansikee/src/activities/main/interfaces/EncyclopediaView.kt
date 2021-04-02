package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.common.models.PlantData

interface EncyclopediaView {

    fun getSearchedPlantsSuccess(loadedPage: Int, searchedPlants: List<PlantData>)
    fun getSearchedPlantsFailed(msg: String?)

    fun getRecentlySearchedPlantsSuccess(recentlySearchedPlants: List<PlantData>)
    fun getRecentlySearchedPlantsFailed(msg: String?)

    fun deleteSearchedPlantSuccess(msg: String)
    fun deleteSearchedPlantFailed(msg: String?)

}