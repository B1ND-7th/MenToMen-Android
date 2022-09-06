package kr.hs.b1nd.intern.mentomen.network

import kr.hs.b1nd.intern.mentomen.network.service.LoginService
import kr.hs.b1nd.intern.mentomen.network.service.TokenService
import kr.hs.b1nd.intern.mentomen.util.Constants.BASE_URL
import kr.hs.b1nd.intern.mentomen.util.Constants.LOGIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    val dAuthRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(LOGIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val loginService: LoginService = dAuthRetrofit.create(LoginService::class.java)
    val mtmService: LoginService = retrofit.create(LoginService::class.java)
    val tokenService: TokenService = retrofit.create(TokenService::class.java)

}