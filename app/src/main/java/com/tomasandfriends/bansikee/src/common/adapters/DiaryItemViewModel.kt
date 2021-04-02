package com.tomasandfriends.bansikee.src.common.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.SimpleDiaryData

class DiaryItemViewModel(diaryData: SimpleDiaryData): ViewModel() {

    val diaryIdx = diaryData.diaryId

    val imageUrl = diaryData.dairyImgUrl
    val writeDate = diaryData.writeDate

    private val _goDetailsEvent = SingleLiveEvent<Int>()
    val goDetailsEvent: LiveData<Int> = _goDetailsEvent

    private val _deleteShowing = MutableLiveData(false)
    val deleteShowing: LiveData<Boolean> = _deleteShowing

    fun setDeleteShowing(b: Boolean){
        _deleteShowing.value = b
    }

    fun itemClick() {
        _goDetailsEvent.value = diaryIdx
    }

    private val _deleteEvent = SingleLiveEvent<Int>()
    val deleteEvent: LiveData<Int> = _deleteEvent

    fun deleteClick() {
        _deleteEvent.value = diaryIdx
    }
}