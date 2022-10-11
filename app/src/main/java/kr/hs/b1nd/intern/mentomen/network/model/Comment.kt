package kr.hs.b1nd.intern.mentomen.network.model

data class Comment(
    val commentId: Int,
    val content: String,
    val createDateTime: String,
    val postId: Int,
    val profileUrl: String,
    val stdInfo: StdInfo,
    val updateDateTime: String,
    val userId: Int,
    val userName: String
)
