package kr.hs.b1nd.intern.mentomen.network.base

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T
)