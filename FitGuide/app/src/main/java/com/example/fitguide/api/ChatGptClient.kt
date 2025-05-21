package com.example.fitguide.api

import android.content.Context
import android.util.Log
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

// Classe personalizada de DNS para lidar com problemas de resolução de nomes
class CustomDns : Dns {
    private val PUBLIC_DNS_SERVERS = listOf(
        "8.8.8.8",      // Google DNS
        "8.8.4.4",      // Google DNS alternativo
        "1.1.1.1",      // Cloudflare DNS
        "1.0.0.1"       // Cloudflare DNS alternativo
    )

    override fun lookup(hostname: String): List<InetAddress> {
        try {
            // Tenta o DNS padrão primeiro
            return Dns.SYSTEM.lookup(hostname)
        } catch (e: UnknownHostException) {
            Log.e("CustomDns", "Falha ao resolver $hostname com DNS padrão: ${e.message}")

            // Se falhar, tenta com servidores DNS públicos
            for (dnsServer in PUBLIC_DNS_SERVERS) {
                try {
                    Log.d("CustomDns", "Tentando resolver $hostname com DNS $dnsServer")
                    val addresses = InetAddress.getAllByName(hostname).toList()
                    if (addresses.isNotEmpty()) {
                        Log.d("CustomDns", "Resolvido com sucesso usando $dnsServer: $addresses")
                        return addresses
                    }
                } catch (e: UnknownHostException) {
                    Log.e("CustomDns", "Falha ao resolver com DNS $dnsServer: ${e.message}")
                    // Continua para o próximo servidor DNS
                }
            }

            // Se todos falharem, lança a exceção original
            throw UnknownHostException("Não foi possível resolver o hostname $hostname com nenhum DNS")
        }
    }
}

object ChatGptClient {
    private const val BASE_URL = "https://api.openai.com/"
    private const val TAG = "ChatGptClient"

    // A chave da API é obtida do arquivo local.properties
    // Este arquivo não é enviado para o controle de versão
    private var apiKey: String = "CHAVE_REMOVIDA_POR_SEGURANCA" // Será substituída em tempo de execução
    private var isInitialized = false

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            // Em produção, use Level.NONE ou BASIC para evitar vazamento de dados sensíveis
            level = HttpLoggingInterceptor.Level.BODY // Usando BODY para depuração
        }

        OkHttpClient.Builder()
            .dns(CustomDns()) // Usa nosso DNS personalizado
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                try {
                    Log.d(TAG, "Enviando requisição: ${request.url}")
                    Log.d(TAG, "Headers: ${request.headers}")

                    // Tenta resolver o host manualmente antes de fazer a requisição
                    val host = request.url.host
                    Log.d(TAG, "Tentando resolver host: $host")

                    val response = chain.proceed(request)
                    Log.d(TAG, "Resposta recebida: ${response.code}")
                    if (!response.isSuccessful) {
                        Log.e(TAG, "Erro na resposta: ${response.code} - ${response.message}")
                        try {
                            val errorBody = response.peekBody(Long.MAX_VALUE).string()
                            Log.e(TAG, "Corpo do erro: $errorBody")
                        } catch (e: Exception) {
                            Log.e(TAG, "Não foi possível ler o corpo do erro", e)
                        }
                    }
                    response
                } catch (e: Exception) {
                    Log.e(TAG, "Erro na requisição HTTP: ${e.message}", e)
                    throw e
                }
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true) // Habilita tentativas de reconexão
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

    /**
     * Inicializa o cliente com o contexto da aplicação
     * Deve ser chamado antes de usar o cliente
     */
    fun initialize(context: Context) {
        if (!isInitialized) {
            apiKey = ApiKeyManager.getApiKey(context)
            isInitialized = true
            Log.d(TAG, "ChatGptClient inicializado com sucesso")
        }
    }

    fun getAuthorizationHeader(): String {
        return "Bearer $apiKey"
    }
}
