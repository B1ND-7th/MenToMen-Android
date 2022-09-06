package kr.hs.b1nd.intern.mentomen.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.request.LoginRequest
import kr.hs.b1nd.intern.mentomen.network.request.MTMLoginRequest
import kr.hs.b1nd.intern.mentomen.network.response.LoginResponse
import kr.hs.b1nd.intern.mentomen.network.response.MTMLoginResponse
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Response
import java.security.MessageDigest

class LoginViewModel(val application: Application): ViewModel() {

    val onClickDauthLogin = SingleLiveEvent<Any>()
    val onClickLoginEvent = SingleLiveEvent<Any>()

    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    fun onClickDauthLogin() {
        onClickDauthLogin.call()
    }

    fun onClickMTMLogin() {
        val call = RetrofitClient.loginService.login(
            LoginRequest(
                id.value.toString(),
                encryptSHA512(pw.value.toString()),
                application.getString(R.string.clientId),
                application.getString(R.string.redirectUrl)
            )
        )

        call.enqueue(object : retrofit2.Callback<BaseResponse<LoginResponse>> {
            override fun onResponse(call: Call<BaseResponse<LoginResponse>>, response: Response<BaseResponse<LoginResponse>>) {
                if (response.isSuccessful) {
                    val call2 = RetrofitClient.mtmService.mtmLogin(
                        MTMLoginRequest(
                            response.body()?.data?.location!!.split("=", "&")[1]
                        )
                    )
                    call2.enqueue(object : retrofit2.Callback<BaseResponse<MTMLoginResponse>> {
                        override fun onResponse(
                            call: Call<BaseResponse<MTMLoginResponse>>,
                            response: Response<BaseResponse<MTMLoginResponse>>
                        ) {
                            if (response.isSuccessful) {
                                App.prefs.setString("accessToken", response.body()?.data!!.accessToken)
                                onClickLoginEvent.call()
                            }
                        }

                        override fun onFailure(
                            call: Call<BaseResponse<MTMLoginResponse>>,
                            t: Throwable
                        ) {

                        }

                    })
                }
            }
            override fun onFailure(call: Call<BaseResponse<LoginResponse>>, t: Throwable) {

            }

        })
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
}