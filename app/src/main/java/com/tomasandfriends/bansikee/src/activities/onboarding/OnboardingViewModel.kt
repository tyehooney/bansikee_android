package com.tomasandfriends.bansikee.src.activities.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomasandfriends.bansikee.src.activities.base.BaseViewModel
import com.tomasandfriends.bansikee.src.activities.onboarding.models.SurveyData

class OnboardingViewModel: BaseViewModel() {

    private val _currentPage = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage

    private val _surveyList = MutableLiveData<List<SurveyData>>()
    val surveyList: LiveData<List<SurveyData>> = _surveyList

    init {
        val tempList = ArrayList<SurveyData>()

        for(i in 1..10){
            tempList.add(
                SurveyData("당신의 정원을 채울\n식물의 향기는 어떨까요? ($i)",
                    "식물의 향기에 대해 원하시는 강도를 선택해주세요.",
                    listOf("강했으면 좋겠어요.", "중간정도가 좋아요.", "약했으면 좋겠어요."))
            )
        }

        _surveyList.value = tempList
    }

    fun onAnswerClick(answerIdx: Int){
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
}