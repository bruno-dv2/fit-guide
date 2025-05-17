package com.example.fitguide.repositories

import com.example.fitguide.BancoTemporario
import com.example.fitguide.Equipamento
import com.example.fitguide.EquipamentoDTO

object EquipamentoRepository {
    // Funções existentes
    suspend fun listarEquipamentos(): List<Equipamento> {
        return BancoTemporario.listaEquipamentos
    }

    suspend fun obterEquipamento(id: Int): Equipamento? {
        return BancoTemporario.listaEquipamentos.find { it.id == id }
    }

    suspend fun criarEquipamento(equipamento: Equipamento): Boolean {
        BancoTemporario.listaEquipamentos.add(equipamento)
        return true
    }

    suspend fun atualizarEquipamento(equipamento: Equipamento): Boolean {
        val index = BancoTemporario.listaEquipamentos.indexOfFirst { it.id == equipamento.id }
        if (index >= 0) {
            BancoTemporario.listaEquipamentos[index] = equipamento
            return true
        }
        return false
    }

    suspend fun deletarEquipamento(id: Int): Boolean {
        val equipamento = BancoTemporario.listaEquipamentos.find { it.id == id }
        if (equipamento != null) {
            BancoTemporario.listaEquipamentos.remove(equipamento)
            return true
        }
        return false
    }

    // Adicionar estas novas funções para suporte ao Supabase
    suspend fun obterEquipamento(id: String): Equipamento? {
        return obterEquipamento(id.toIntOrNull() ?: return null)
    }

    suspend fun criarEquipamento(dto: EquipamentoDTO): Boolean {
        // Converter DTO para Equipamento
        val equipamento = Equipamento(
            nome = dto.nome,
            descricaoTecnica = dto.descricao_tecnica,
            imagem = dto.imagem_url
        )
        return criarEquipamento(equipamento)
    }

    suspend fun atualizarEquipamento(dto: EquipamentoDTO): Boolean {
        val id = dto.id?.toIntOrNull() ?: return false
        val equipamentoExistente = obterEquipamento(id) ?: return false

        val equipamentoAtualizado = Equipamento(
            nome = dto.nome,
            descricaoTecnica = dto.descricao_tecnica,
            imagem = dto.imagem_url,
            id = id
        )

        return atualizarEquipamento(equipamentoAtualizado)
    }

    suspend fun deletarEquipamento(id: String): Boolean {
        return deletarEquipamento(id.toIntOrNull() ?: return false)
    }
}