package com.tomasandfriends.bansikee.src.activities.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.sign_up.interfaces.SignUpView
import com.tomasandfriends.bansikee.src.activities.sign_up.models.SignUpBody
import com.tomasandfriends.bansikee.src.common.interfaces.CheckNicknameView
import com.tomasandfriends.bansikee.src.common.services.CheckNicknameService

class SignUpViewModel : BaseViewModel(), SignUpView, CheckNicknameView {

    val nickname = MutableLiveData<String?>()

    private val _checkNickname = MutableLiveData<Int>()
    val checkNickname : LiveData<Int> get() = _checkNickname

    private val _nicknameDuplicated = MutableLiveData(true)
    val nicknameDuplicated: LiveData<Boolean> = _nicknameDuplicated

    private var lastCheckedNickname = ""

    val email = MutableLiveData<String>()

    private val _checkEmail = MutableLiveData<Int>()
    val checkEmail : LiveData<Int> get() = _checkEmail

    val password = MutableLiveData<String>()

    val passwordCheck = MutableLiveData<String>()

    private val _checkPassword = MutableLiveData<Int>()
    val checkPassword : LiveData<Int> get() = _checkPassword

    private val signUpService = SignUpService(this)
    private val checkNicknameService = CheckNicknameService(this)

    //check nickname duplicated
    override fun checkNickname(strNickname: String) {
        checkNicknameService.checkNickname(strNickname)
    }

    override fun checkNicknameSuccess(duplicated: Boolean) {
        _nicknameDuplicated.value = duplicated
        _checkNickname.value =
                if(duplicated)
                    R.string.nickname_duplicated
                else{
                    R.string.nickname_veryfied
                }

        if(!duplicated) lastCheckedNickname = nickname.value!!
    }

    override fun checkNicknameFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

    // sign up
    fun signUp(){
        _clearInput.value = null

        val strEmail = email.value
        val strNickname = nickname.value
        val strPw = password.value
        val strPwCheck = passwordCheck.value

        _checkEmail.value = if(strEmail.isNullOrEmpty()) R.string.pls_write_email else null
        _checkPassword.value =
                if(strPw.isNullOrEmpty() || strPwCheck.isNullOrEmpty())
                    R.string.pls_write_password
                else if(strPw != strPwCheck)
                    R.string.password_not_same
                else null
        _checkNickname.value =
                when {
                    strNickname.isNullOrEmpty() -> R.string.pls_write_nickname
                    lastCheckedNickname != strNickname -> R.string.check_nickname_pls
                    else -> null
                }

        if(!strEmail.isNullOrEmpty() && !strNickname.isNullOrEmpty()
                && !strPw.isNullOrEmpty() && !strPwCheck.isNullOrEmpty()
                && strPw == strPwCheck && !nicknameDuplicated.value!! && lastCheckedNickname == strNickname)
            signUpService.signUp(SignUpBody(strEmail, strNickname, strPw, strPwCheck))
    }

    //when sign up api success
    override fun signUpSuccess(msg: String) {
        _toastMessage.value = msg
        _finishEvent.value = null
    }

    //when sign up api failed
    override fun signUpFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}