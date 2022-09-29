package kr.hs.b1nd.intern.mentomen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.Comment
import kr.hs.b1nd.intern.mentomen.network.model.Post
import kr.hs.b1nd.intern.mentomen.network.model.StdInfo
import retrofit2.Call
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val itemList = MutableLiveData<List<Comment>>()

    val postId = MutableLiveData<Int>()

    val content = MutableLiveData<String>()
    val imgUrl = MutableLiveData<String>()
    val createDateTime = MutableLiveData<String>()
    val stdInfo = MutableLiveData<StdInfo>()
    val profileUrl = MutableLiveData<String>()
    val userName = MutableLiveData<String>()

    fun readOne() {
        val call = RetrofitClient.postService.readOne(postId.value!!)

        call.enqueue(object : retrofit2.Callback<BaseResponse<Post>> {
            override fun onResponse(
                call: Call<BaseResponse<Post>>,
                response: Response<BaseResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    content.value = response.body()?.data!!.content
                    imgUrl.value = response.body()?.data!!.imgUrl
                    createDateTime.value = response.body()?.data!!.createDateTime
                    stdInfo.value = response.body()?.data!!.stdInfo
                    profileUrl.value = response.body()?.data!!.profileUrl
                    userName.value = response.body()?.data!!.userName
                }
            }

            override fun onFailure(call: Call<BaseResponse<Post>>, t: Throwable) {
            }

        })
    }

    fun readComment() {
        val call = RetrofitClient.commentService.readComment(postId.value!!)

        call.enqueue(object : retrofit2.Callback<BaseResponse<List<Comment>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Comment>>>,
                response: Response<BaseResponse<List<Comment>>>
            ) {
                if (response.isSuccessful) {
                    itemList.value = response.body()?.data ?: emptyList()
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Comment>>>, t: Throwable) {

            }

        })
    }


}