package com.tomasandfriends.bansikee.src.activities.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.OnboardingView
import com.tomasandfriends.bansikee.src.activities.onboarding.models.SurveyData

class OnboardingViewModel: BaseViewModel(), OnboardingView {

    private val _currentPage = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage

    private val _surveyList = MutableLiveData<List<SurveyData>>()
    val surveyList: LiveData<List<SurveyData>> = _surveyList

    private val onBoardingService = OnBoardingService(this)

    init {
        onBoardingService.getSurveyList()
    }

    fun onOptionClick(answerIdx: Int){
        val currentSelectedIdx = surveyList.value!![currentPage.value!!].selectedIdx
        currentSelectedIdx.value = if(currentSelectedIdx.value == answerIdx) -1 else answerIdx
    }

    fun onBackClick(){
        if (currentPage.value!! > 0){
            _currentPage.value = currentPage.value!! - 1
        } else {
            finish()
        }
    }

    fun onNextClick(){
        if (currentPage.value!! < surveyList.value!!.size-1){
            _currentPage.value = currentPage.value!! + 1
        } else {
            finish()
        }
    }

    override fun getSurveySuccess(surveyList: List<SurveyData>) {
        _surveyList.value = surveyList
    }

    override fun getSurveyFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}