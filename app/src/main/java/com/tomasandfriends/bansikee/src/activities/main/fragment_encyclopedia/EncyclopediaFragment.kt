package com.tomasandfriends.bansikee.src.activities.main.fragment_encyclopedia

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentEncyclopediaBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment

class EncyclopediaFragment: BaseFragment<FragmentEncyclopediaBinding, EncyclopediaViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_encyclopedia
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(EncyclopediaViewModel::class.java)
        binding.viewModel = viewModel
    }

}