package com.tomasandfriends.bansikee.src.activities.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel

class SignUpViewModel : BaseViewModel() {

    val nickname = MutableLiveData<String>()

    private val _checkNickname = MutableLiveData<Int>()
    val checkNickname : LiveData<Int> get() = _checkNickname

    val email = MutableLiveData<String>()

    private val _checkEmail = MutableLiveData<Int>()
    val checkEmail : LiveData<Int> get() = _checkEmail

    val password = MutableLiveData<String>()

    val passwordCheck = MutableLiveData<String>()

    private val _checkPassword = MutableLiveData<Int>()
    val checkPassword : LiveData<Int> get() = _checkPassword

    init {}

    fun signUp(){}
}