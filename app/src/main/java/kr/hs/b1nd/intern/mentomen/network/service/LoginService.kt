package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.request.LoginRequest
import kr.hs.b1nd.intern.mentomen.network.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest) : Call<LoginResponse>
}