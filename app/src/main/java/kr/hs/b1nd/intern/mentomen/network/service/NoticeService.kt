package kr.hs.b1nd.intern.mentomen.network.service

import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Notice
import kr.hs.b1nd.intern.mentomen.network.model.NoticeStatus
import retrofit2.Call
import retrofit2.http.GET

interface NoticeService {
    @GET("notice/check")
    fun checkNotice(
    ): Call<BaseResponse<NoticeStatus>>

    @GET("notice/list")
    fun getNotice(
    ): Call<BaseResponse<List<Notice>>>
}