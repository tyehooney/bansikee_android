package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentMyPageBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment

class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_page
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(MyPageViewModel::class.java)
        binding.viewModel = viewModel
    }

}