package com.tomasandfriends.bansikee.src.activities.main.fragment_home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentHomeBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(HomeViewModel::class.java)
        binding.viewModel = viewModel
    }

}