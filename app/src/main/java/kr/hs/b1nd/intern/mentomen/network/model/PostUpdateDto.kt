package kr.hs.b1nd.intern.mentomen.network.model

data class PostUpdateDto(
    val content: String,
    val imgUrls: List<ImgUrl?> = emptyList(),
    val postId: Int,
    val tag: String
)
