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

        val register = settingForDodam("39bc523458c14eb987b7b16175426a31a9f105b7f5814f1f9eca7d454bd23c73", "e90b070b437f420eb788fad746e97a507984328ddf9142f481397ca6e7afda0e", "http://localhost:3000/callback")
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