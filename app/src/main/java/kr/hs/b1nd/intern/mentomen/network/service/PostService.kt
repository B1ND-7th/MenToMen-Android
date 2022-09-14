package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("post/read-all")
    fun readAll(): Call<BaseResponse<List<Post>>>

    @GET("post/read-all/{tag}")
    fun readTag(
        @Path("tag") tag: String
    ): Call<BaseResponse<List<Post>>>

    @GET("post/read-one/{postId}")
    fun readOne(
        @Path("postId") postId: Int
    ): Call<BaseResponse<Post>>

}