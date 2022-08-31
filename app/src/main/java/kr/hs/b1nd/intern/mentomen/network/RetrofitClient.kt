package kr.hs.b1nd.intern.mentomen.network

import com.google.gson.GsonBuilder
import kr.hs.b1nd.intern.mentomen.network.service.LoginService
import kr.hs.b1nd.intern.mentomen.util.Constants.BASE_URL
import kr.hs.b1nd.intern.mentomen.util.Constants.LOGIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val gson = GsonBuilder().setLenient().create()
    private val intercepter = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val logger = OkHttpClient.Builder().addInterceptor(intercepter)
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .writeTimeout(100, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(logger)
        .build()

    val retrofitLogin: Retrofit = Retrofit.Builder()
        .baseUrl(LOGIN_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(logger)
        .build()

    val loginService: LoginService = retrofitLogin.create(LoginService::class.java)
    val mtmService: LoginService = retrofit.create(LoginService::class.java)

}