package com.example.fitguide.repositories

import com.example.fitguide.BancoTemporario
import com.example.fitguide.Exercicio
import com.example.fitguide.ExercicioDTO

object ExercicioRepository {
    // Funções existentes
    suspend fun listarExercicios(): List<Exercicio> {
        return BancoTemporario.listaExercicios
    }

    suspend fun listarExerciciosPorCategoria(categoria: String): List<Exercicio> {
        return BancoTemporario.listaExercicios.filter { it.categoria == categoria }
    }

    suspend fun obterExercicio(id: Int): Exercicio? {
        return BancoTemporario.listaExercicios.find { it.id == id }
    }

    suspend fun criarExercicio(exercicio: Exercicio): Boolean {
        BancoTemporario.listaExercicios.add(exercicio)
        return true
    }

    suspend fun atualizarExercicio(exercicio: Exercicio): Boolean {
        val index = BancoTemporario.listaExercicios.indexOfFirst { it.id == exercicio.id }
        if (index >= 0) {
            BancoTemporario.listaExercicios[index] = exercicio
            return true
        }
        return false
    }

    suspend fun deletarExercicio(id: Int): Boolean {
        val exercicio = BancoTemporario.listaExercicios.find { it.id == id }
        if (exercicio != null) {
            BancoTemporario.listaExercicios.remove(exercicio)
            return true
        }
        return false
    }

    // Adicionar estas novas funções para suporte ao Supabase
    suspend fun obterExercicio(id: String): Exercicio? {
        return obterExercicio(id.toIntOrNull() ?: return null)
    }

    suspend fun criarExercicio(dto: ExercicioDTO): Boolean {
        // Converter DTO para Exercicio
        val exercicio = Exercicio(
            nome = dto.nome,
            equipamento = dto.equipamento_id ?: "",  // Temporário
            descricaoTecnica = dto.descricao_tecnica,
            musculosTrabalhados = dto.musculos_trabalhados,
            linkVideo = dto.link_video,
            categoria = dto.categoria
        )
        return criarExercicio(exercicio)
    }

    suspend fun atualizarExercicio(dto: ExercicioDTO): Boolean {
        val id = dto.id?.toIntOrNull() ?: return false
        val exercicioExistente = obterExercicio(id) ?: return false

        val exercicioAtualizado = Exercicio(
            nome = dto.nome,
            equipamento = dto.equipamento_id ?: exercicioExistente.equipamento,
            descricaoTecnica = dto.descricao_tecnica,
            musculosTrabalhados = dto.musculos_trabalhados,
            linkVideo = dto.link_video,
            categoria = dto.categoria,
            id = id
        )

        return atualizarExercicio(exercicioAtualizado)
    }

    suspend fun deletarExercicio(id: String): Boolean {
        return deletarExercicio(id.toIntOrNull() ?: return false)
    }
}