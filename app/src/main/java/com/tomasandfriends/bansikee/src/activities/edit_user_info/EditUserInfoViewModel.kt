package com.tomasandfriends.bansikee.src.activities.edit_user_info

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.ApplicationClass.Companion.USER_IMG
import com.tomasandfriends.bansikee.ApplicationClass.Companion.USER_NAME
import com.tomasandfriends.bansikee.ApplicationClass.Companion.mSharedPreferences
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.add_my_plant.models.AddPlantBody
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.edit_user_info.interfaces.EditUserInfoView
import com.tomasandfriends.bansikee.src.common.interfaces.CheckNicknameView
import com.tomasandfriends.bansikee.src.common.services.CheckNicknameService
import java.io.File

class EditUserInfoViewModel: BaseViewModel(), CheckNicknameView, EditUserInfoView {

    private val _editProfileImg = _userImg
    val editProfileImg: LiveData<String?> = _editProfileImg

    val editNickname = _userName
    private val originalNickname = userName.value!!
    private var lastCheckedNickname = userName.value!!

    private val _checkNickname = MutableLiveData<Int>()
    val checkNickname : LiveData<Int> get() = _checkNickname

    private val _nicknameDuplicated = MutableLiveData(true)
    val nicknameDuplicated: LiveData<Boolean> = _nicknameDuplicated

    private val _editImgEvent = SingleLiveEvent<Void?>()
    val editImgEvent: LiveData<Void?> = _editImgEvent

    fun editImgClick(){
        _editImgEvent.value = null
    }

    fun setPhotoStrUrl(strImgUrl: String){
        _editProfileImg.value = strImgUrl
    }

    private val _editFinishEvent = SingleLiveEvent<Void?>()
    val editFinishEvent: LiveData<Void?> = _editFinishEvent

    fun editFinishClick(){
        _editFinishEvent.value = null
    }

    private val checkNicknameService = CheckNicknameService(this)

    override fun checkNickname(strNickname: String) {
        checkNicknameService.checkNickname(strNickname)
    }

    override fun checkNicknameSuccess(duplicated: Boolean) {
        if (editNickname.value!! != originalNickname){

            _nicknameDuplicated.value = duplicated
            _checkNickname.value =
                if(duplicated)
                    R.string.nickname_duplicated
                else{
                    R.string.nickname_veryfied
                }

            if(!duplicated) lastCheckedNickname = editNickname.value!!

        }
    }

    private val editUserInfoService = EditUserInfoService(this)

    fun editUserInfo(){
        _loading.value = true

        val strNickname = editNickname.value

        _checkNickname.value =
            when {
                strNickname.isNullOrEmpty() -> R.string.pls_write_nickname
                lastCheckedNickname != strNickname -> R.string.check_nickname_pls
                else -> null
            }

        if (_checkNickname.value == null){
            if (!editProfileImg.value.isNullOrEmpty() && editProfileImg.value!! != userImg.value!!){

                val storage = FirebaseStorage.getInstance()
                val uriFromFile = Uri.fromFile(File(editProfileImg.value!!))
                val fileName = uriFromFile.lastPathSegment
                val reference = storage.getReference("/Images/$fileName")

                reference.putFile(uriFromFile).addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        editUserInfoService.editUserInfo(strNickname!!, it.toString())
                    }.addOnFailureListener {
                        _loading.value = false
                        _snackbarMessage.value = it.message
                    }
                }.addOnFailureListener {
                    _loading.value = false
                    _snackbarMessage.value = it.message
                }

            } else {
                editUserInfoService.editUserInfo(strNickname!!, editProfileImg.value!!)
            }
        }
    }

    override fun checkNicknameFailed(msg: String?) {
        _loading.value = false
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

    override fun editUserInfoSuccess(msg: String) {
        _loading.value = false
        _toastMessage.value = msg

        val editor = mSharedPreferences!!.edit()
        editor.putString(USER_NAME, editNickname.value!!)
        editor.putString(USER_IMG, editProfileImg.value!!).apply()

        finish()
    }

    override fun editUserInfoFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}