package com.tomasandfriends.bansikee.src.common.adapters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.my_plant_details.models.SimpleDiaryData

class DiaryItemViewModel(diaryData: SimpleDiaryData): ViewModel() {

    val imageUrl = diaryData.dairyImgUrl
    @RequiresApi(Build.VERSION_CODES.O)
    val writeDate = diaryData.getTrimmedStartDate()

    private val _goDetailsEvent = SingleLiveEvent<Int>()
    val goDetailsEvent: LiveData<Int> = _goDetailsEvent

    fun itemClick() {
        //TODO: go DiaryDetailsActivity
    }
}