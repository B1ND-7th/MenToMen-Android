package kr.hs.b1nd.intern.mentomen.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityLoginBinding
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.request.LoginRequest
import kr.hs.b1nd.intern.mentomen.network.response.LoginResponse
import kr.hs.b1nd.intern.mentomen.viewmodel.LoginViewModel
import kr.hs.dgsw.smartschool.dauth.api.network.DAuth.loginForDodam
import kr.hs.dgsw.smartschool.dauth.api.network.DAuth.settingForDodam
import retrofit2.Call
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private var refreshToken: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        performViewModel()

        val register = settingForDodam(
            applicationContext.getString(R.string.clientId),
            applicationContext.getString(R.string.clientSecret),
            applicationContext.getString(R.string.redirectUrl)
        )
        loginViewModel.onClickLoginEvent.observe(this) {
            loginForDodam(register, { tokenResponse ->
                refreshToken = tokenResponse.refreshToken
                token = tokenResponse.token
                startActivity(Intent(this, MainActivity::class.java))
            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })
        }

        /*val raw = binding.etPw.text.toString()
        val md = MessageDigest.getInstance("SHA-512")
        md.update(raw.toByteArray())
        val hex = java.lang.String.format("%0128x", BigInteger(1, md.digest()))*/

        binding.btnLogin.setOnClickListener {
            val call = RetrofitClient.loginService.login(
                LoginRequest(
                    binding.etId.text.toString(),
                    encryptSHA512(binding.etPw.text.toString()),
                    applicationContext.getString(R.string.clientId),
                    applicationContext.getString(R.string.redirectUrl)
                )
            )

            call.enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.body()?.status == 200) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        Toast.makeText(this@LoginActivity, "로그인에 성공하였습니다!", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("LoginActivity", response.body()?.data?.location!!.split("=", "&")[0] ?: "")
                    } else {
                        Toast.makeText(this@LoginActivity, "로그인에 실패!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    fun encryptSHA512(target: String): String {
        val messageDigest =
            MessageDigest.getInstance("SHA-512")
        val encryptedPassword = StringBuilder()
        messageDigest.update(target.toByteArray())
        val buffer = messageDigest.digest()
        for (temp in buffer) {
            var sb =
                StringBuilder(Integer.toHexString(temp.toInt()))
            while (sb.length < 2) {
                sb.insert(0, "0")
            }
            sb = StringBuilder(sb.substring(sb.length - 2))
            encryptedPassword.append(sb)
        }
        return encryptedPassword.toString()
    }

    private fun performViewModel() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.vm = loginViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

    }
}