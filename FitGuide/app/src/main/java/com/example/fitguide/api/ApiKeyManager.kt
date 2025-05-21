package com.example.fitguide.api

import android.content.Context
import android.util.Log
import java.io.FileInputStream
import java.util.Properties

/**
 * Classe para gerenciar a chave da API de forma segura
 * Lê a chave do arquivo local.properties que não é enviado para o controle de versão
 */
object ApiKeyManager {
    private const val TAG = "ApiKeyManager"
    private const val PROPERTIES_FILE = "local.properties"
    private const val API_KEY_PROPERTY = "openai.api.key"
    
    // Chave de fallback para desenvolvimento (não contém uma chave real)
    private const val FALLBACK_API_KEY = "CHAVE_REMOVIDA_POR_SEGURANCA"
    
    /**
     * Obtém a chave da API do arquivo local.properties
     * Se não conseguir ler o arquivo, retorna uma chave de fallback
     */
    fun getApiKey(context: Context): String {
        try {
            // Tenta ler o arquivo local.properties na raiz do projeto
            val properties = Properties()
            val projectDir = context.applicationContext.filesDir.parentFile?.parentFile?.parentFile?.parentFile?.parentFile
            
            if (projectDir != null) {
                val localPropertiesFile = java.io.File(projectDir, PROPERTIES_FILE)
                
                if (localPropertiesFile.exists()) {
                    FileInputStream(localPropertiesFile).use { input ->
                        properties.load(input)
                    }
                    
                    val apiKey = properties.getProperty(API_KEY_PROPERTY)
                    if (!apiKey.isNullOrBlank()) {
                        Log.d(TAG, "Chave da API lida com sucesso do arquivo local.properties")
                        return apiKey
                    }
                }
            }
            
            // Se não conseguir ler do arquivo, tenta ler dos assets
            context.assets.open(PROPERTIES_FILE).use { input ->
                properties.load(input)
                
                val apiKey = properties.getProperty(API_KEY_PROPERTY)
                if (!apiKey.isNullOrBlank()) {
                    Log.d(TAG, "Chave da API lida com sucesso dos assets")
                    return apiKey
                }
            }
            
            Log.w(TAG, "Não foi possível ler a chave da API, usando chave de fallback")
            return FALLBACK_API_KEY
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao ler a chave da API: ${e.message}", e)
            return FALLBACK_API_KEY
        }
    }
}
