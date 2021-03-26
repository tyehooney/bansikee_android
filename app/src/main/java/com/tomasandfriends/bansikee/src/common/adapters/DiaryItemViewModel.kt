package com.tomasandfriends.bansikee.src.common.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.SimpleDiaryData

class DiaryItemViewModel(diaryData: SimpleDiaryData): ViewModel() {

    private val diaryId = diaryData.diaryId

    val imageUrl = diaryData.dairyImgUrl
    val writeDate = diaryData.writeDate

    private val _goDetailsEvent = SingleLiveEvent<Int>()
    val goDetailsEvent: LiveData<Int> = _goDetailsEvent

    fun itemClick() {
        _goDetailsEvent.value = diaryId
    }
}