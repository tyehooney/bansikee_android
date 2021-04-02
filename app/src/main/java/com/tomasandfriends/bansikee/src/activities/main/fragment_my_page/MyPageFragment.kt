package com.tomasandfriends.bansikee.src.activities.main.fragment_my_page

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.tomasandfriends.bansikee.ApplicationClass
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
                .setPositiveButton(R.string.yes) { _, _ ->
                    disconnectAll(false)
                    Toast.makeText(requireContext(), R.string.logout_finished, Toast.LENGTH_SHORT).show()
                    backToLogin()
                }.setNegativeButton(R.string.no, null)
                .create().show()
        })

        viewModel.withdrawalClickEvent.observe(viewLifecycleOwner, {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.withdrawal)
                .setMessage(R.string.notice_withdrawal)
                .setPositiveButton(R.string.yes) {_,_ ->
                    viewModel.withdrawal()
                }.setNegativeButton(R.string.no, null)
                .create().show()
        })

        viewModel.withdrawalFinishEvent.observe(viewLifecycleOwner, {
            disconnectAll(true)
            backToLogin()
        })
    }

    private fun disconnectAll(withdrawal: Boolean){
        val kakaoClient = UserApiClient.instance
        if (withdrawal) kakaoClient.unlink {}
        else kakaoClient.logout {}

        Firebase.auth.signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        if (withdrawal) googleSignInClient?.revokeAccess()
        else googleSignInClient?.signOut()
    }

    private fun backToLogin(){
        ApplicationClass.mSharedPreferences!!.edit().clear().apply()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        requireContext().startActivity(intent)
    }
}