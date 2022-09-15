package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.PostSubmitDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostService {

    @GET("post/read-all")
    fun readAll(): Call<BaseResponse<List<Post>>>

    @GET("post/read-one/{postId}")
    fun readOne(
        @Path("postId") postId: Int
    ): Call<BaseResponse<Post>>

    @POST
    fun submitPost(
        @Body postSubmitDto : PostSubmitDto
    ):Call<BaseResponse<Any>>




}