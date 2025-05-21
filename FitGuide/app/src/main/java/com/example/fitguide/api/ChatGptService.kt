package com.example.fitguide.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatGptService {
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: ChatGptRequest
    ): Response<ChatGptResponse>
}
