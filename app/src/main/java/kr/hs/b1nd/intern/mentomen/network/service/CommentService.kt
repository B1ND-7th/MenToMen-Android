package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Comment
import kr.hs.b1nd.intern.mentomen.network.model.CommentSubmitDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @GET("comment/read/{postId}")
    fun readComment(
        @Path("postId") postId: Int
    ): Call<BaseResponse<List<Comment>>>

    @POST("comment/submit")
    fun submitComment(
        @Body commentSubmitDto: CommentSubmitDto
    ): Call<BaseResponse<Unit>>
}