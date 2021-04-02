package com.tomasandfriends.bansikee.src.activities.diary.interfaces

import com.tomasandfriends.bansikee.src.activities.diary.models.DiaryDetailsData

interface DiaryView {

    fun getDiaryDetailsSuccess(diaryDetails: DiaryDetailsData)
    fun getDiaryDetailsFailed(msg: String?)

    fun addDiarySuccess(msg: String)
    fun addDiaryFailed(msg: String?)

}