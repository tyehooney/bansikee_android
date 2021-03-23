package com.tomasandfriends.bansikee.src.activities.main.interfaces

import com.tomasandfriends.bansikee.src.activities.main.models.MyPlantData

interface MyGardenView {

    fun getMyPlantsSuccess(myPlantList: List<MyPlantData>)
    fun getMyPlantsFailed(msg: String?)

}