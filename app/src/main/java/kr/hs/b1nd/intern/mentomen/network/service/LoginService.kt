package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.request.DAuthLoginRequest
import kr.hs.b1nd.intern.mentomen.network.request.LoginRequest
import kr.hs.b1nd.intern.mentomen.network.response.DAuthLoginResponse
import kr.hs.b1nd.intern.mentomen.network.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    fun login(@Body DAuthLoginRequest: DAuthLoginRequest) : Call<BaseResponse<DAuthLoginResponse>>

    @POST("auth/code")
    fun mtmLogin(@Body loginRequest: LoginRequest) : Call<BaseResponse<LoginResponse>>
}