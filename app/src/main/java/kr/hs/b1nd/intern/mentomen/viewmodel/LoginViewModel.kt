package kr.hs.b1nd.intern.mentomen.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.request.DAuthLoginRequest
import kr.hs.b1nd.intern.mentomen.network.request.LoginRequest
import kr.hs.b1nd.intern.mentomen.network.response.DAuthLoginResponse
import kr.hs.b1nd.intern.mentomen.network.response.ErrorResponse
import kr.hs.b1nd.intern.mentomen.network.response.LoginResponse
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.security.MessageDigest

class LoginViewModel(private val application: Application): ViewModel() {

    val onClickLoginEvent = SingleLiveEvent<Unit>()
    val failLoginEvent = SingleLiveEvent<Unit>()

    val profileImage = MutableLiveData<String?>()

    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    fun onClickLogin() {
        val dAuthCall = RetrofitClient.loginService.login(
            DAuthLoginRequest(
                id.value.toString(),
                encryptSHA512(pw.value.toString()),
                application.getString(R.string.clientId),
                application.getString(R.string.redirectUrl)
            )
        )

        dAuthCall.enqueue(object : retrofit2.Callback<BaseResponse<DAuthLoginResponse>> {
            override fun onResponse(dAuthCall: Call<BaseResponse<DAuthLoginResponse>>, responseDAuth: Response<BaseResponse<DAuthLoginResponse>>) {
                if (responseDAuth.isSuccessful) {
                    profileImage.value = responseDAuth.body()?.data!!.profileImage
                    val call = RetrofitClient.mtmService.mtmLogin(
                        LoginRequest(
                            responseDAuth.body()?.data!!.location.split("=", "&")[1]
                        )
                    )
                    call.enqueue(object : retrofit2.Callback<BaseResponse<LoginResponse>> {
                        override fun onResponse(
                            call: Call<BaseResponse<LoginResponse>>,
                            response: Response<BaseResponse<LoginResponse>>
                        ) {
                            if (response.isSuccessful) {
                                App.prefs.setString("accessToken", response.body()?.data!!.accessToken)
                                App.prefs.setString("refreshToken", response.body()?.data!!.refreshToken)
                                onClickLoginEvent.call()
                            }
                        }

                        override fun onFailure(
                            call: Call<BaseResponse<LoginResponse>>,
                            t: Throwable
                        ) {
                            Toast.makeText(application, "서버가 꺼져있습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    val errorBody = responseDAuth.errorBody()?.let {
                        RetrofitClient.retrofit.responseBodyConverter<ErrorResponse>(
                            ErrorResponse::class.java, ErrorResponse::class.java.annotations).convert(
                            it
                        )
                    }
                    Toast.makeText(application, errorBody?.message, Toast.LENGTH_SHORT).show()
                    failLoginEvent.call()

                }
            }
            override fun onFailure(dAuthCall: Call<BaseResponse<DAuthLoginResponse>>, t: Throwable) {

            }

        })
    }

    private fun encryptSHA512(target: String): String {
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