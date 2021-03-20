package com.tomasandfriends.bansikee.src.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.tomasandfriends.bansikee.R
import com.tomasandfriends.bansikee.databinding.ActivityLoginBinding
import com.tomasandfriends.bansikee.src.activities.base.BaseActivity
import com.tomasandfriends.bansikee.src.activities.main.MainActivity
import com.tomasandfriends.bansikee.src.activities.sign_up.SignUpActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        const val REQUEST_GOOGLE_SIGN_IN = 100
    }

    private val TAG = javaClass.name

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun setViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.goSignUpEvent.observe(this, { startActivity(Intent(this, SignUpActivity::class.java)) })

        viewModel.kakaoLoginEvent.observe(this, { kakaoLogin() })

        viewModel.googleLoginEvent.observe(this, { googleLogin() })

        viewModel.goMainActivityEvent.observe(this, {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // kakao login
    private fun kakaoLogin() {
        if (LoginClient.instance.isKakaoTalkLoginAvailable(this)){
            LoginClient.instance.loginWithKakaoTalk(this, callback = kakaoLoginCallback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(this, callback = kakaoLoginCallback)
        }
    }

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
        }
        else if (token != null) {
            Log.i(TAG, "로그인 성공 ${token.accessToken}")
            //토큰 검사(있으면 로그인, 없으면 회원가입)
            viewModel.kakaoLogin(token.accessToken)
        }
    }

    // google login
    private fun googleLogin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account : GoogleSignInAccount? = null
            try{
                account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "google account : "+ account.email)
            } catch (e: ApiException){
                Log.w(TAG, "Google sign in failed", e)
            }

            //토큰 검사(있으면 로그인, 없으면 회원가입)
            if(account != null)
                viewModel.googleLogin(account.idToken!!)
        }
    }
}