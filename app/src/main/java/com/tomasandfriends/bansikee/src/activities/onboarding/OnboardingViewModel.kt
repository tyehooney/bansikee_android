package com.tomasandfriends.bansikee.src.activities.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.ApplicationClass
import com.tomasandfriends.bansikee.src.SingleLiveEvent
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.onboarding.interfaces.OnboardingView
import com.tomasandfriends.bansikee.src.activities.onboarding.models.AnswerBody
import com.tomasandfriends.bansikee.src.activities.onboarding.models.SurveyData

class OnboardingViewModel: BaseViewModel(), OnboardingView {

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    private val _surveyList = MutableLiveData<List<SurveyData>>()
    val surveyList: LiveData<List<SurveyData>> = _surveyList

    private val _goSurveyResultEvent = SingleLiveEvent<Void?>()
    val goSurveyResultEvent: LiveData<Void?> = _goSurveyResultEvent

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
            answerSurvey()
        }
    }

    private fun answerSurvey(){
        var answerList: MutableList<AnswerBody> = ArrayList()
        for(surveyData in surveyList.value!!){
            answerList.add(AnswerBody(surveyData.selectedIdx.value!!, surveyData.questionIndex))
        }

        onBoardingService.answerSurvey(answerList)
    }

    override fun getSurveySuccess(surveyList: List<SurveyData>) {
        _surveyList.value = surveyList
        _currentPage.value = 0
    }

    override fun getSurveyFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }

    override fun answerSurveySuccess(msg: String) {
        _currentPage.value = -1
        _goSurveyResultEvent.value = null
    }

    override fun answerSurveyFailed(msg: String?) {
        _snackbarMessage.value = msg ?: ApplicationClass.NETWORK_ERROR
    }
}