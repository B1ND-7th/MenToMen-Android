package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.request.LoginRequest
import kr.hs.b1nd.intern.mentomen.network.request.MTMLoginRequest
import kr.hs.b1nd.intern.mentomen.network.response.LoginResponse
import kr.hs.b1nd.intern.mentomen.network.response.MTMLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest) : Call<BaseResponse<LoginResponse>>

    @POST("auth/code")
    fun mtmLogin(@Body loginRequest: MTMLoginRequest) : Call<BaseResponse<MTMLoginResponse>>
}