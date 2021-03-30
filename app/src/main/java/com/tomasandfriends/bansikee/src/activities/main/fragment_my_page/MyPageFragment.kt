package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.FragmentMyPageBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseFragment
import com.tomasandfriends.bansikee.src.activities.edit_user_info.EditUserInfoActivity
import com.tomasandfriends.bansikee.src.activities.liking_plants.LikingPlantsActivity
import com.tomasandfriends.bansikee.src.activities.login.LoginActivity

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
            startActivity(Intent(requireContext(), LikingPlantsActivity::class.java))
        })

        viewModel.logoutClickEvent.observe(viewLifecycleOwner, {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.logout)
                .setMessage(R.string.ask_before_logout)
                .setPositiveButton(R.string.yes) {_,_ ->
                    viewModel.logout()
                }.setNegativeButton(R.string.no, null)
                .create().show()
        })

        viewModel.logoutFinishEvent.observe(viewLifecycleOwner, {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            requireContext().startActivity(intent)
        })

        viewModel.withdrawalClickEvent.observe(viewLifecycleOwner, {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.withdrawal)
                .setMessage(R.string.notice_withdrawal)
                .setPositiveButton(R.string.yes) {_,_ ->}
                .setNegativeButton(R.string.no, null)
                .create().show()
        })
    }

}