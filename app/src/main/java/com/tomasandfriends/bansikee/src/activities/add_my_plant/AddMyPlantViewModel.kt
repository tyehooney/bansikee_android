package com.tomasandfriends.bansikee.src.activities.add_my_plant

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.add_my_plant.interfaces.AddMyPlantView
import com.tomasandfriends.bansikee.src.activities.add_my_plant.models.AddPlantBody
import com.tomasandfriends.bansikee.src.activities.add_my_plant.models.EditMyPlantBody
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class AddMyPlantViewModel : BaseViewModel(), AddMyPlantView {

    private var plantIdx = 0
    private val _myPlantIdx = MutableLiveData(0)
    val myPlantIdx: LiveData<Int> = _myPlantIdx

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

    private val _plantName = MutableLiveData<String>()
    val plantName: LiveData<String> = _plantName

    private val _plantSpecies = MutableLiveData<String>()
    val plantSpecies: LiveData<String> = _plantSpecies

    private val _plantImage = MutableLiveData("")
    val plantImage: LiveData<String> = _plantImage

    private val _getPhotoEvent = SingleLiveEvent<Void?>()
    val getPhotoEvent: LiveData<Void?> = _getPhotoEvent

    val myPlantName = MutableLiveData<String?>()

    private val _startDate = MutableLiveData(Date())
    val startDate: LiveData<Date> = _startDate

    private val _setDateEvent = SingleLiveEvent<Void?>()
    val setDateEvent: LiveData<Void?> = _setDateEvent

    val myPlantIntro = MutableLiveData<String>()
    val wateringTerms = MutableLiveData("7")

    private val addMyPlantService = AddMyPlantService(this)

    fun getPlantData(plantIdx: Int, plantName: String, plantSpecies: String){
        this.plantIdx = plantIdx
        this._plantName.value = plantName
        this._plantSpecies.value = plantSpecies
    }

    fun getMyPlantDataToEdit(bundle: Bundle){
        _myPlantIdx.value = bundle.getInt("myPlantIdx")
        plantIdx = bundle.getInt("plantIdx")
        _plantName.value = bundle.getString("plantName")
        myPlantName.value = bundle.getString("nickname")
        _plantImage.value = bundle.getString("imgUrl")
        myPlantIntro.value = bundle.getString("plantIntro")
        wateringTerms.value = bundle.getInt("waterPeriod").toString()

        val localDate = LocalDateTime.parse(bundle.getString("startDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        _startDate.value = Date.from(localDate.atZone( ZoneId.systemDefault()).toInstant())
    }

    fun getPhoto(){
        _getPhotoEvent.value = null
    }

    fun setPhotoStrUrl(strImgUrl: String){
        _plantImage.value = strImgUrl
    }

    fun setDateClick(){
        _setDateEvent.value = null
    }

    fun setStartDate(cal: Calendar){
        _startDate.value = cal.time
    }

    fun editMyPlantClick(){
        if (_myPlantIdx.value == 0) addToMyPlants()
        else editMyPlant()
    }

    private fun addToMyPlants(){
        when {
            myPlantName.value.isNullOrEmpty() -> _snackbarMessage.value = "이름을 입력해주세요!"
            myPlantIntro.value.isNullOrEmpty() -> _snackbarMessage.value = "내 식물 한줄 소개를 작성해주세요!"
            else -> {
                _loading.value = true

                val ldtStartDate = startDate.value!!.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                val imageUrlToUpload = plantImage.value
                var addPlantBody: AddPlantBody

                if(!imageUrlToUpload.isNullOrEmpty()){
                    val storage = FirebaseStorage.getInstance()
                    val uriFromFile = Uri.fromFile(File(plantImage.value!!))
                    val fileName = uriFromFile.lastPathSegment
                    val reference = storage.getReference("/Images/$fileName")

                    reference.putFile(uriFromFile).addOnSuccessListener {
                        reference.downloadUrl.addOnSuccessListener {
                            addPlantBody = AddPlantBody(it.toString(), ldtStartDate.toString(),
                                plantIdx, myPlantIntro.value!!, myPlantName.value!!, wateringTerms.value!!.toInt())

                            addMyPlantService.addToMyPlants(addPlantBody)
                        }.addOnFailureListener {
                            _loading.value = false
                            _snackbarMessage.value = it.message
                        }
                    }.addOnFailureListener {
                        _loading.value = false
                        _snackbarMessage.value = it.message
                    }
                } else {
                    addPlantBody = AddPlantBody(imageUrlToUpload!!, ldtStartDate.toString(),
                        plantIdx, myPlantIntro.value!!, myPlantName.value!!, wateringTerms.value!!.toInt())

                    addMyPlantService.addToMyPlants(addPlantBody)
                }
            }
        }
    }

    private fun editMyPlant(){
        when {
            myPlantName.value.isNullOrEmpty() -> _snackbarMessage.value = "이름을 입력해주세요!"
            myPlantIntro.value.isNullOrEmpty() -> _snackbarMessage.value = "내 식물 한줄 소개를 작성해주세요!"
            else -> {
                _loading.value = true

                val ldtStartDate = startDate.value!!.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                val imageUrlToUpload = plantImage.value
                var editMyPlantBody: EditMyPlantBody

                if(!imageUrlToUpload.isNullOrEmpty() && !imageUrlToUpload.startsWith("https://")){
                    val storage = FirebaseStorage.getInstance()
                    val uriFromFile = Uri.fromFile(File(plantImage.value!!))
                    val fileName = uriFromFile.lastPathSegment
                    val reference = storage.getReference("/Images/$fileName")

                    reference.putFile(uriFromFile).addOnSuccessListener {
                        reference.downloadUrl.addOnSuccessListener {
                            editMyPlantBody = EditMyPlantBody(myPlantIdx.value!!, it.toString(), ldtStartDate.toString(),
                                    plantIdx, myPlantIntro.value!!, myPlantName.value!!, wateringTerms.value!!.toInt())

                            addMyPlantService.editMyPlant(editMyPlantBody)
                        }.addOnFailureListener {
                            _loading.value = false
                            _snackbarMessage.value = it.message
                        }
                    }.addOnFailureListener {
                        _loading.value = false
                        _snackbarMessage.value = it.message
                    }
                } else {
                    editMyPlantBody = EditMyPlantBody(myPlantIdx.value!!, imageUrlToUpload!!, ldtStartDate.toString(),
                            plantIdx, myPlantIntro.value!!, myPlantName.value!!, wateringTerms.value!!.toInt())

                    addMyPlantService.editMyPlant(editMyPlantBody)
                }
            }
        }
    }

    override fun addToMyPlantsSuccess(msg: String) {
        _loading.value = false
        _toastMessage.value = msg
        finish()
    }

    override fun addToMyPlantsFailed(msg: String?) {
        _loading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}