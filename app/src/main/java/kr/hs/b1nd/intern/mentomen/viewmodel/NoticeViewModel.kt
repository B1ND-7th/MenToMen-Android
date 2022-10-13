package kr.hs.b1nd.intern.mentomen.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Notice
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Response

class NoticeViewModel: ViewModel() {
    val itemList = MutableLiveData<List<Notice>>()

    fun callNotice() {
        val call = RetrofitClient.noticeService.getNotice()

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<Notice>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Notice>>>,
                response: Response<BaseResponse<List<Notice>>>
            ) {
                if (response.isSuccessful) {
                    itemList.value = response.body()?.data!!
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Notice>>>, t: Throwable) {
            }

        })
    }
}