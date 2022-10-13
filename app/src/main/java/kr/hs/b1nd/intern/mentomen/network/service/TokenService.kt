package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.response.TokenResponse
import retrofit2.Call
import retrofit2.http.GET

interface TokenService {
    @GET("auth/refreshToken")
    fun refreshToken() : Call<BaseResponse<TokenResponse>>
}