package com.example.fitguide

import kotlinx.serialization.Serializable

@Serializable
data class EquipamentoDTO(
    val id: String? = null,
    val nome: String,
    val descricao_tecnica: String,
    val imagem_url: String,
    val created_by: String? = null
)

@Serializable
data class ExercicioDTO(
    val id: String? = null,
    val nome: String,
    val equipamento_id: String?,
    val descricao_tecnica: String,
    val musculos_trabalhados: String,
    val link_video: String,
    val categoria: String,
    val created_by: String? = null
)

@Serializable
data class UsuarioDTO(
    val id: String? = null,
    val email: String,
    val nome: String,
    val peso: String,
    val altura: String,
    val tipo: String, // "aluno" ou "professor"
    val professor_id: String? = null
)