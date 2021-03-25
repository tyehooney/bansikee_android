package com.tomasandfriends.bansikee.src.activities.diary.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import com.tomasandfriends.bansikee.src.utils.SystemUtils.getDayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DiaryDetailsResponse: DefaultResponse() {

    @SerializedName("data")
    val diaryData = DiaryDetailsData()

}

class DiaryDetailsData {

    @SerializedName("contents")
    val contents = ""

    @SerializedName("diaryPictures")
    val diaryPictures = DiaryPicturesData()

    @SerializedName("height")
    val height = 0

    @SerializedName("myDiaryId")
    val diaryId = 0

    @SerializedName("nickName")
    val nickname = ""

    @SerializedName("watered")
    val watered = ""

    @SerializedName("weather")
    val weather = ""

    @SerializedName("writeDate")
    val writeDate = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStrDate(): String{
        var result = ""

        val localDate = LocalDateTime.parse(writeDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        result = "${localDate.format(ApplicationClass.localDateTimeFormat)} ${getDayOfWeek(localDate.dayOfWeek.value)}"

        return result
    }
}

class DiaryPicturesData {

    @SerializedName("urls")
    val imgUrls = ArrayList<String>()

}