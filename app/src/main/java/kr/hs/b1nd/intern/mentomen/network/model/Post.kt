package kr.hs.b1nd.intern.mentomen.network.model

data class Post(
    val content: String,
    val imgUrl: String,
    val localDateTime: String,
    val postId: Int,
    val profileUrl: String,
    val stdInfo: StdInfo,
    val tag: String,
    val userName: String
)