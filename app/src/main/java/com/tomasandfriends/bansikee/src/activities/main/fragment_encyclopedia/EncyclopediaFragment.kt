package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentEncyclopediaBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment
import com.tomasandfriends.bansikee.src.activities.onboarding.OnboardingActivity

class EncyclopediaFragment: BaseFragment<FragmentEncyclopediaBinding, EncyclopediaViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_encyclopedia
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(EncyclopediaViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.goOnboardingEvent.observe(viewLifecycleOwner, {
            startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getRecommendations()
    }
}