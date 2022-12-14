package kr.hs.b1nd.intern.mentomen.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityLoginBinding
import kr.hs.b1nd.intern.mentomen.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        performViewModel()
        observeViewModel()
    }

    private fun observeViewModel() = with(loginViewModel) {
        binding.autoLogin.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                App.prefs.autoLogin()
        }

        if (App.prefs.isLogin())
            onClickLoginEvent.call()

        onClickLoginEvent.observe(this@LoginActivity) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        failLoginEvent.observe(this@LoginActivity) {
            binding.etPw.setText("")
        }
    }

    private fun performViewModel() {
        loginViewModel = LoginViewModel(application)
        binding.vm = loginViewModel
        binding.lifecycleOwner = this
    }
}