package com.tomasandfriends.bansikee.src.activities.my_plant_details

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSimpleDateFormat
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.my_plant_details.interfaces.MyPlantDetailsView
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.MyPlantDetailsData
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.SimpleDiaryData
import com.tomasandfriends.bansikee.src.common.adapters.DiaryAdapter
import com.tomasandfriends.bansikee.src.common.adapters.DiaryItemViewModel
import java.util.*
import kotlin.collections.ArrayList

class MyPlantDetailsViewModel: BaseViewModel(), MyPlantDetailsView, DiaryAdapter.DeleteMyDiaryListener {

    private var myPlantIdx = 0
    private var plantIdx = 0

    private val _myPlantDetails = MutableLiveData<MyPlantDetailsData>()
    val myPlantDetails: LiveData<MyPlantDetailsData> = _myPlantDetails

    private val _myPlantDiaryItems = MutableLiveData<List<DiaryItemViewModel>>()
    val myPlantDiaryItems: LiveData<List<DiaryItemViewModel>> = _myPlantDiaryItems

    private val _getDiaryListLoading = MutableLiveData(true)
    val getDiaryListLoading: LiveData<Boolean> = _getDiaryListLoading

    private val _drawerOpen = MutableLiveData(false)
    val drawerOpen: LiveData<Boolean> = _drawerOpen

    private val _goPlantDetailsEvent = SingleLiveEvent<Int>()
    val goPlantDetailsEvent: LiveData<Int> = _goPlantDetailsEvent

    private val _goEditMyPlantEvent = SingleLiveEvent<Bundle>()
    val goEditMyPlantEvent: LiveData<Bundle> = _goEditMyPlantEvent

    private val _goWriteDiaryEvent = SingleLiveEvent<Bundle>()
    val goWriteDiaryEvent: LiveData<Bundle> = _goWriteDiaryEvent

    private val _deleteMyDiaryEvent = SingleLiveEvent<Int>()
    val deleteMtDiaryEvent: LiveData<Int> = _deleteMyDiaryEvent

    private val _goDiaryListFragmentEvent = SingleLiveEvent<Void?>()
    val goDiaryListFragmentEvent: LiveData<Void?> = _goDiaryListFragmentEvent

    private val _didWriteTodaysDiary = MutableLiveData(false)
    val didWriteTodaysDiary: LiveData<Boolean> = _didWriteTodaysDiary

    private val myPlantDetailsService = MyPlantDetailsService(this)

    fun getMyPlantDetails(myPlantIdx: Int){
        this.myPlantIdx = myPlantIdx
        _getDiaryListLoading.value = true
        myPlantDetailsService.getMyPlantDetails(myPlantIdx)
        myPlantDetailsService.getDiaryList(myPlantIdx)
    }

    override fun getMyPlantDetailsSuccess(myPlantDetailsData: MyPlantDetailsData) {
        _myPlantDetails.value = myPlantDetailsData
        plantIdx = myPlantDetailsData.plantIdx
    }

    override fun getMyPlantDetailsFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

    override fun getDiaryListSuccess(diaryList: List<SimpleDiaryData>) {
        val results = ArrayList<DiaryItemViewModel>()
        for (simpleDiary in diaryList)
            results.add(DiaryItemViewModel(simpleDiary))

        if (diaryList.isNotEmpty()){
            val strToday = mSimpleDateFormat.format(Date())
            _didWriteTodaysDiary.value = strToday.equals(diaryList[0].writeDate)
        }

        _myPlantDiaryItems.value = results
        _getDiaryListLoading.value = false
    }

    override fun getDiaryListFailed(msg: String?) {
        _getDiaryListLoading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

    fun drawerClick(){
        _drawerOpen.value = !drawerOpen.value!!
    }

    fun goPlantDetailsClick(){
        _goPlantDetailsEvent.value = plantIdx
    }

    fun goEditMyPlantClick(){

        val bundle = Bundle()
        val currentMyPlantData = myPlantDetails.value!!

        bundle.putInt("myPlantIdx", currentMyPlantData.myPlantIdx)
        bundle.putString("plantName", currentMyPlantData.plantName)
        bundle.putInt("plantIdx", currentMyPlantData.plantIdx)
        bundle.putString("imgUrl", currentMyPlantData.profileImgUrl)
        bundle.putString("startDate", currentMyPlantData.startDateTime)
        bundle.putString("plantIntro", currentMyPlantData.contents)
        bundle.putString("nickname", currentMyPlantData.nickname)
        bundle.putInt("waterPeriod", currentMyPlantData.waterPeriod)

        _goEditMyPlantEvent.value = bundle

    }

    fun goDiaryListFragmentClick(){
        _goDiaryListFragmentEvent.value = null
    }

    fun goWriteDairyClick(){

        val bundle = Bundle()
        bundle.putString("myPlantName", myPlantDetails.value!!.nickname)
        bundle.putInt("dDay", myPlantDetails.value!!.dDay)
        bundle.putInt("myPlantIdx", myPlantIdx)

        _goWriteDiaryEvent.value = bundle
    }

    override fun onDeleteClick(myDiaryIdx: Int) {
        _deleteMyDiaryEvent.value = myDiaryIdx
    }

    fun deleteMyDiary(myDiaryIdx: Int){
        myPlantDetailsService.deleteDiary(myDiaryIdx)
    }

    override fun deleteDiarySuccess(msg: String) {
        _toastMessage.value = msg
        myPlantDetailsService.getDiaryList(myPlantIdx)
    }

    override fun deleteDiaryFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}