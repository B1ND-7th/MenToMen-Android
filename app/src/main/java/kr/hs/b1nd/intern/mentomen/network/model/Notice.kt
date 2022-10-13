package kr.hs.b1nd.intern.mentomen.network.model

data class Notice (
    val commentContent: String,
    val createDateTime: String,
    val noticeStatus: String,
    val postId: Int,
    val senderName: String,
    val senderProfileImage: String
)