package kr.hs.b1nd.intern.mentomen.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityLoginBinding
import kr.hs.b1nd.intern.mentomen.viewmodel.HomeViewModel
import kr.hs.b1nd.intern.mentomen.viewmodel.LoginViewModel
import kr.hs.dgsw.smartschool.dauth.api.network.DAuth.loginForDodam
import kr.hs.dgsw.smartschool.dauth.api.network.DAuth.settingForDodam

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private var refreshToken: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        performViewModel()

        val register = settingForDodam(applicationContext.getString(R.string.clientId), applicationContext.getString(R.string.clientSecret), applicationContext.getString(R.string.redirectUrl))
        loginViewModel.onClickLoginEvent.observe(this) {
             loginForDodam(register, { tokenResponse ->
                 refreshToken = tokenResponse.refreshToken
                 token = tokenResponse.token
                 startActivity(Intent(this, MainActivity::class.java))
             }, { error ->
                 Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
             })

        }
    }

    private fun performViewModel() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.vm = loginViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}