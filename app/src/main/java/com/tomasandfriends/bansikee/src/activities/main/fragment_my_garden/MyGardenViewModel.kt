package com.tomasandfriends.bansikee.src.activities.main.fragment_my_garden

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.main.interfaces.MyGardenView
import com.tomasandfriends.bansikee.src.common.adapters.MyPlantItemViewModel
import com.tomasandfriends.bansikee.src.activities.main.models.MyPlantData

class MyGardenViewModel : BaseViewModel(), MyGardenView {

    val searchingWord = MutableLiveData<String>()

    private val _myPlantItems = MutableLiveData<List<MyPlantItemViewModel>>()
    val myPlantItems: LiveData<List<MyPlantItemViewModel>> = _myPlantItems

    private val _getMyPlantsLoading = MutableLiveData(true)
    val getMyPlantsLoading: LiveData<Boolean> = _getMyPlantsLoading

    private val myGardenService = MyGardenService(this)

    fun getMyPlants(){
        _getMyPlantsLoading.value = true
        myGardenService.getMyPlants()
    }

    override fun getMyPlantsSuccess(myPlantList: List<MyPlantData>) {
        val resultViewModels = ArrayList<MyPlantItemViewModel>()

        for (plantData in myPlantList)
            resultViewModels.add(MyPlantItemViewModel(plantData))

        _myPlantItems.value = resultViewModels
        _getMyPlantsLoading.value = false
    }

    override fun getMyPlantsFailed(msg: String?) {
        _getMyPlantsLoading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}