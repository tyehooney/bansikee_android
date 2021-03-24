package com.tomasandfriends.bansikee.src.activities.my_plant_details.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import com.tomasandfriends.bansikee.ApplicationClass.Companion.localDateTimeFormat
import com.tomasandfriends.bansikee.src.common.models.DefaultResponse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyPlantDetailsResponse: DefaultResponse() {

    @SerializedName("data")
    val myPlantDetailsData = MyPlantDetailsData()

}

class MyPlantDetailsData {

    @SerializedName("myPlantId")
    val myPlantIdx = 0

    @SerializedName("nickName")
    val nickname = ""

    @SerializedName("plantId")
    val plantIdx = 0

    @SerializedName("plantName")
    val plantName = ""

    @SerializedName("plantTip")
    val plantTip = ""

    @SerializedName("dday")
    val dDay = 0

    @SerializedName("myPlantProfileUrl")
    val profileImgUrl = ""

    @SerializedName("startDate")
    val startDateTime = ""

    @SerializedName("contents")
    val contents = ""

    @SerializedName("water")
    val waterPeriod = 0

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTrimmedStartDate(): String{
        val localDate = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        return localDate.format(localDateTimeFormat)
    }
}

class DiaryListResponse: DefaultResponse() {

    @SerializedName("listData")
    val diaryList = ArrayList<SimpleDiaryData>()

}

class SimpleDiaryData {

    @SerializedName("diaryProfile")
    val dairyImgUrl = ""

    @SerializedName("writeDate")
    val writeDate = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTrimmedStartDate(): String{
        val localDate = LocalDateTime.parse(writeDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        return localDate.format(localDateTimeFormat)
    }
}