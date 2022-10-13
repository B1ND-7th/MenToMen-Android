package kr.hs.b1nd.intern.mentomen.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.network.RetrofitClient
import kr.hs.b1nd.intern.mentomen.network.base.BaseResponse
import kr.hs.b1nd.intern.mentomen.network.model.*
import kr.hs.b1nd.intern.mentomen.network.response.ErrorResponse
import kr.hs.b1nd.intern.mentomen.network.response.TokenResponse
import kr.hs.b1nd.intern.mentomen.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val deletePostEvent = SingleLiveEvent<Unit>()
    val successCommentEvent = SingleLiveEvent<Unit>()
    val successReadEvent = SingleLiveEvent<Unit>()

    val itemList = MutableLiveData<List<Comment>>()
    val userId = MutableLiveData<Int>()
    val author = MutableLiveData<Int>()
    val postId = MutableLiveData<Int>()
    val tag = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val imgUrl = MutableLiveData<List<String?>>()
    val createDateTime = MutableLiveData<String>()
    val stdInfo = MutableLiveData<StdInfo>()
    val profileUrl = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val commentContent = MutableLiveData<String>()

    fun readOne() {
        val call = RetrofitClient.postService.readOnePost(postId.value!!)

        call.enqueue(object : retrofit2.Callback<BaseResponse<Post>> {
            override fun onResponse(
                call: Call<BaseResponse<Post>>,
                response: Response<BaseResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    content.value = response.body()?.data!!.content
                    imgUrl.value = response.body()?.data!!.imgUrls
                    createDateTime.value = response.body()?.data!!.createDateTime
                    stdInfo.value = response.body()?.data!!.stdInfo
                    profileUrl.value = response.body()?.data!!.profileUrl
                    userName.value = response.body()?.data!!.userName
                    tag.value = response.body()?.data!!.tag
                    author.value = response.body()?.data!!.author
                    Log.d("test123", "onResponse: author")
                    successReadEvent.call()
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
                if (response.isSuccessful)
                    itemList.value = response.body()?.data ?: emptyList()
            }
            override fun onFailure(call: Call<BaseResponse<List<Comment>>>, t: Throwable) {
            }
        })
    }

    fun getUser() {
        val call = RetrofitClient.userService.getUserInfo()

        call.enqueue(object : retrofit2.Callback<BaseResponse<User>> {
            override fun onResponse(
                call: Call<BaseResponse<User>>,
                response: Response<BaseResponse<User>>
            ) {
                if (response.isSuccessful) {
                    Log.d("test123", "onResponse: userId")
                    userId.value = response.body()?.data!!.userId
                }
            }

            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
            }

        })
    }

    fun postComment() {
        val call = RetrofitClient.commentService.submitComment(
            CommentSubmitDto(
                commentContent.value!!,
                postId.value!!
            )
        )

        call.enqueue(object : retrofit2.Callback<BaseResponse<Unit>> {
            override fun onResponse(
                call: Call<BaseResponse<Unit>>,
                response: Response<BaseResponse<Unit>>
            ) {
                if (response.isSuccessful)
                    successCommentEvent.call()
                else {
                    val errorBody = response.errorBody()?.let {
                        RetrofitClient.retrofit.responseBodyConverter<ErrorResponse>(
                            ErrorResponse::class.java, ErrorResponse::class.java.annotations).convert(it)
                    }
                    if (errorBody?.status == 500) {
                        refreshToken()
                        postComment()
                    }
                }
            }
            override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
            }
        })
    }

    private fun refreshToken() {
        val call = RetrofitClient.tokenService.refreshToken()

        call.enqueue(object : retrofit2.Callback<BaseResponse<TokenResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<TokenResponse>>,
                response: Response<BaseResponse<TokenResponse>>
            ) {
                if (response.isSuccessful)
                    App.prefs.setString("accessToken", response.body()?.data!!.accessToken)
            }

            override fun onFailure(call: Call<BaseResponse<TokenResponse>>, t: Throwable) {
            }
        })
    }

    fun deletePost() {
        Log.d("test123", "onResponse: ${postId.value}")
        val call = RetrofitClient.postService.deletePost(postId.value!!)
        call.enqueue(object : retrofit2.Callback<BaseResponse<Unit>> {
            override fun onResponse(
                call: Call<BaseResponse<Unit>>,
                response: Response<BaseResponse<Unit>>
            ) {
                if (response.isSuccessful)
                    deletePostEvent.call()
            }
            override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
            }
        })
    }
}