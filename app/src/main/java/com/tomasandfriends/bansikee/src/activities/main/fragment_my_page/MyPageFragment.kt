package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentMyPageBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment
import com.tomasandfriends.bansikee.src.activities.edit_user_info.EditUserInfoActivity

class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_page
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)
                .get(MyPageViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.goEditMyInfoEvent.observe(viewLifecycleOwner, {
            startActivity(Intent(requireContext(), EditUserInfoActivity::class.java))
        })

        viewModel.goMyLikingPlantsEvent.observe(viewLifecycleOwner, {

        })
    }

}