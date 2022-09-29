package kr.hs.b1nd.intern.mentomen.network.model

data class CommentSubmitDto(
    val content: String,
    val postId: Int
)
