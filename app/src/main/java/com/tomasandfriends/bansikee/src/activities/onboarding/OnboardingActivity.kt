package com.tomasandfriends.bansikee.src.activities.onboarding

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityOnboardingBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding, OnboardingViewModel>() {

    private var surveyFragments: MutableList<SurveyFragment> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_onboarding
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(OnboardingViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.surveyList.observe(this, {
            surveyFragments.clear()
            for(i in it.indices)
                surveyFragments.add(SurveyFragment.newInstance(i))
        })

        viewModel.currentPage.observe(this, {
            if(!surveyFragments.isNullOrEmpty() && it >= 0)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.onboarding_container, surveyFragments[it])
                        .commit()
        })

        viewModel.goSurveyResultEvent.observe(this, {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.onboarding_container, SurveyResultFragment())
                    .commit()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        viewModel.onBackClick()
    }
}