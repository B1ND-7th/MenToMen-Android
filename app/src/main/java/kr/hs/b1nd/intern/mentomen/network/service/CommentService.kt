package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Comment
import kr.hs.b1nd.intern.mentomen.network.model.CommentSubmitDto
import kr.hs.b1nd.intern.mentomen.network.model.CommentUpdateDto
import retrofit2.Call
import retrofit2.http.*

interface CommentService {
    @GET("comment/read/{postId}")
    fun readComment(
        @Path("postId") postId: Int
    ): Call<BaseResponse<List<Comment>>>

    @POST("comment/submit")
    fun submitComment(
        @Body commentSubmitDto: CommentSubmitDto
    ): Call<BaseResponse<Unit>>

    @DELETE("comment/delete/{id}")
    fun deleteComment(
        @Path("id") id: Int
    ): Call<BaseResponse<Unit>>

    @PATCH("comment/update")
    fun updateComment(
        @Body commentUpdateDto: CommentUpdateDto
    ): Call<BaseResponse<Unit>>
}