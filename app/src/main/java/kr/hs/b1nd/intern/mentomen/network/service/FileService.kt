package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.ImgUrls
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileService {

    @Multipart
    @POST("file/upload")
    fun loadImage(
        @Part imageFile: List<MultipartBody.Part?>
    ): Call<BaseResponse<List<ImgUrls?>>>
}