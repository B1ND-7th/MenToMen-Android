package kr.hs.b1nd.intern.mentomen.network.model

data class PostSubmitDto(
    val content : String,
    val imgUrls : List<ImgUrls?> = emptyList(),
    val tag : String
)
