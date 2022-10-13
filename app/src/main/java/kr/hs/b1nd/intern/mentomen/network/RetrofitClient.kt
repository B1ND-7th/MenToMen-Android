package kr.hs.b1nd.intern.mentomen.network

import kr.hs.b1nd.intern.mentomen.network.service.*
import kr.hs.b1nd.intern.mentomen.util.Constants.BASE_URL
import kr.hs.b1nd.intern.mentomen.util.Constants.LOGIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(TokenInterceptor())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val dAuthRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(LOGIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val loginService: LoginService = dAuthRetrofit.create(LoginService::class.java)
    val mtmService: LoginService = retrofit.create(LoginService::class.java)
    val postService: PostService = retrofit.create(PostService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)
    val fileService: FileService = retrofit.create(FileService::class.java)
    val commentService: CommentService = retrofit.create(CommentService::class.java)
    val tokenService: TokenService = retrofit.create(TokenService::class.java)
    val noticeService: NoticeService = retrofit.create(NoticeService::class.java)
}