package com.tomasandfriends.bansikee.src.activities.onboarding

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentSurveyResultBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment

class SurveyResultFragment: BaseFragment<FragmentSurveyResultBinding, SurveyResultViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_survey_result
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(SurveyResultViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.onboardAgainEvent.observe(viewLifecycleOwner, {
            startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
            requireActivity().finish()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getResult()
    }
}