package com.example.fitguide.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ChatGptClient {
    private const val BASE_URL = "https://api.openai.com/"
    private const val TAG = "ChatGptClient"

    // IMPORTANTE: Esta é uma chave de API da OpenAI
    // Formato para API normal: "sk-abcdefghijklmnopqrstuvwxyz123456789"
    // Formato para API de organização: "Bearer org-abcdefghijklmnopqrstuvwxyz123456789"
    private const val API_KEY = "CHAVE_REMOVIDA_POR_SEGURANCA" // Substitua pela sua chave real antes de executar o app

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            // Em produção, use Level.NONE ou BASIC para evitar vazamento de dados sensíveis
            level = HttpLoggingInterceptor.Level.BASIC
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                try {
                    val response = chain.proceed(request)
                    response // Retorna a resposta explicitamente
                } catch (e: Exception) {
                    Log.e(TAG, "Erro na requisição HTTP: ${e.message}")
                    throw e
                }
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: ChatGptService by lazy {
        retrofit.create(ChatGptService::class.java)
    }

    fun getAuthorizationHeader(): String {
        return "Bearer $API_KEY"
    }
}
