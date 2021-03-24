package com.tomasandfriends.bansikee.src.activities.my_plant_details.interfaces

import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.MyPlantDetailsData
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.SimpleDiaryData

interface MyPlantDetailsView {

    fun getMyPlantDetailsSuccess(myPlantDetailsData: MyPlantDetailsData)
    fun getMyPlantDetailsFailed(msg: String?)

    fun getDiaryListSuccess(diaryList: List<SimpleDiaryData>)
    fun getDiaryListFailed(msg: String?)

}