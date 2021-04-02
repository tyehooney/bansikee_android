package com.tomasandfriends.bansikee.src.activities.main.fragment_home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentHomeBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment
import com.tomasandfriends.bansikee.src.activities.onboarding.OnboardingActivity
import com.tomasandfriends.bansikee.src.activities.plant_details.PlantDetailsActivity

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.goEncyclopediaEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.encyclopedia)
        })

        viewModel.goPlantDetailsEvent.observe(viewLifecycleOwner, {
            val intent = Intent(requireContext(), PlantDetailsActivity::class.java)
            intent.putExtra("plantIdx", it)
            intent.putExtra("status","search")
            startActivity(intent)
        })

        viewModel.goMyGardenEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.my_garden)
        })

        viewModel.goOnboardingEvent.observe(viewLifecycleOwner, {
            startActivity(Intent(requireContext(), OnboardingActivity::class.java))
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.callHomeData()
    }

    override fun onStop() {
        super.onStop()
        viewModel.setLoading(true)
    }
}