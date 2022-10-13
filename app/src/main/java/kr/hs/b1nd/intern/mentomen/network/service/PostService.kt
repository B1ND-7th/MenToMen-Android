package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.PostSubmitDto
import kr.hs.b1nd.intern.mentomen.network.model.PostUpdateDto
import retrofit2.Call
import retrofit2.http.*

interface PostService {

    @GET("post/read-all")
    fun readPost(
    ): Call<BaseResponse<List<Post>>>

    @GET("post/read-all/{tag}")
    fun readTagPost(
        @Path("tag") tag: String
    ): Call<BaseResponse<List<Post>>>

    @GET("post/read-one/{postId}")
    fun readOnePost(
        @Path("postId") postId: Int
    ): Call<BaseResponse<Post>>

    @POST("post/submit")
    fun submitPost(
        @Body postSubmitDto: PostSubmitDto
    ): Call<BaseResponse<Unit>>

    @DELETE("post/delete/{postId}")
    fun deletePost(
        @Path("postId") postId: Int
    ): Call<BaseResponse<Unit>>

    @PATCH("post/update")
    fun updatePost(
        @Body postUpdateDto: PostUpdateDto
    ): Call<BaseResponse<Unit>>

    @GET("post/search/{keyWord}")
    fun searchPost(
        @Path("keyWord") keyWord: String
    ): Call<BaseResponse<List<Post>>>

}