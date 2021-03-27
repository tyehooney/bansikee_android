package com.tomasandfriends.bansikee.src.activities.diary

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSimpleDateFormat
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.diary.interfaces.DiaryView
import com.tomasandfriends.bansikee.src.activities.diary.models.AddDiaryBody
import com.tomasandfriends.bansikee.src.activities.diary.models.DiaryDetailsData
import com.tomasandfriends.bansikee.src.activities.diary.models.DiaryPicturesData
import com.tomasandfriends.bansikee.src.utils.SystemUtils.getDayOfWeek
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class DiaryViewModel: BaseViewModel(), DiaryView {
    var diaryIdx = 0
    private var myPlantIdx = 0

    private val _diaryDate = MutableLiveData<String>()
    val diaryDate: LiveData<String> = _diaryDate

    private val _diaryImages = MutableLiveData<List<String>>()
    val diaryImages: LiveData<List<String>> = _diaryImages

    val currentPage = MutableLiveData(1)

    private val _getPhotosEvent = SingleLiveEvent<Void?>()
    val getPhotosEvent: LiveData<Void?> = _getPhotosEvent

    private val _dDay = MutableLiveData<Int>()
    val dDay: LiveData<Int> = _dDay

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private val _weather = MutableLiveData("GOOD")
    val weather: LiveData<String> = _weather

    private val _height = MutableLiveData(0)
    val height: LiveData<Int> = _height

    val watered = MutableLiveData(false)

    val diaryContents = MutableLiveData("")

    private val _editHeightEvent = SingleLiveEvent<Int>()
    val editHeightEvent: LiveData<Int> = _editHeightEvent

    private val _finishDiaryEvent = SingleLiveEvent<Void?>()
    val finishDiaryEvent: LiveData<Void?> = _finishDiaryEvent

    private val diaryService = DiaryService(this)

    fun initWriteDiary(bundle: Bundle){
        val today = Calendar.getInstance()
        val dateToday = today.time
        _diaryDate.value = "${mSimpleDateFormat.format(dateToday)} ${getDayOfWeek(today.get(Calendar.DAY_OF_WEEK))}"

        _dDay.value = bundle.getInt("dDay")+1
        _nickname.value = bundle.getString("myPlantName")
        myPlantIdx = bundle.getInt("myPlantIdx")
    }

    fun getDiaryData(diaryIdx: Int){
        this.diaryIdx = diaryIdx
        diaryService.getDiaryDetails(diaryIdx)
    }

    override fun getDiaryDetailsSuccess(diaryDetails: DiaryDetailsData) {
        diaryContents.value = diaryDetails.contents
        _diaryImages.value = diaryDetails.diaryPictures.imgUrls
        _dDay.value = diaryDetails.dayFromStart+1
        _height.value = diaryDetails.height
        _nickname.value = diaryDetails.nickname
        _weather.value = diaryDetails.weather
        watered.value = diaryDetails.watered == "YES"
        _diaryDate.value = diaryDetails.getStrDate()
    }

    override fun getDiaryDetailsFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

    fun getPhotosClick() {
        _getPhotosEvent.value = null
    }

    fun setPhotos(imgUrls: ArrayList<String>){
        _diaryImages.value = imgUrls
    }

    fun weatherClick(strWeather: String){
        _weather.value = strWeather
    }

    fun editHeightClick(){
        _editHeightEvent.value = height.value!!
    }

    fun setHeight(newHeight: Int){
        _height.value = newHeight
    }

    fun finishDiaryClick(){
        _finishDiaryEvent.value = null
    }

    fun addDiary(){
        _loading.value = true

        val strWatered = if (watered.value!!) "YES" else "NO"

        if (!diaryImages.value.isNullOrEmpty()){
            val uploadedImgUrls = ArrayList<String>()
            val storage = FirebaseStorage.getInstance()

            for (i in diaryImages.value!!.indices){
                val imgUrl = diaryImages.value!![i]
                val uriFromFile = Uri.fromFile(File(imgUrl))
                val fileName = uriFromFile.lastPathSegment
                val reference = storage.getReference("/Images/$fileName")

                reference.putFile(uriFromFile).addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        uploadedImgUrls.add(it.toString())
                        if (i == diaryImages.value!!.size-1){
                            val addDiaryBody = AddDiaryBody(myPlantIdx,
                                    DiaryPicturesData(uploadedImgUrls),
                                    weather.value!!,
                                    height.value!!,
                                    strWatered,
                                    diaryContents.value!!)

                            diaryService.addDiary(addDiaryBody)
                        }
                    }
                }
            }
        } else {
            val addDiaryBody = AddDiaryBody(myPlantIdx,
                    DiaryPicturesData(ArrayList()),
                    weather.value!!,
                    height.value!!,
                    strWatered,
                    diaryContents.value!!)

            diaryService.addDiary(addDiaryBody)
        }
    }

    override fun addDiarySuccess(msg: String) {
        _loading.value = false
        _toastMessage.value = msg
        finish()
    }

    override fun addDiaryFailed(msg: String?) {
        _loading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}