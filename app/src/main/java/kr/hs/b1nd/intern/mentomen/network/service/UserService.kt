package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.User
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("user/my")
    fun getUserInfo(
    ): Call<BaseResponse<User>>

    @GET("user/post")
    fun getMyPost(
    ): Call<BaseResponse<List<Post>>>
}