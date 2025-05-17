package com.example.fitguide.repositories

import com.example.fitguide.BancoTemporario
import com.example.fitguide.Usuario
import com.example.fitguide.UsuarioDTO

object UsuarioRepository {
    // Funções existentes
    suspend fun listarAlunos(professorId: String): List<Usuario> {
        // Aqui você filtraria por professorId, mas vamos apenas retornar todos os usuários por enquanto
        return BancoTemporario.listaUsuarios
    }

    suspend fun obterUsuario(id: Int): Usuario? {
        return BancoTemporario.listaUsuarios.find { it.id == id }
    }

    suspend fun atualizarUsuario(usuario: Usuario): Boolean {
        val index = BancoTemporario.listaUsuarios.indexOfFirst { it.id == usuario.id }
        if (index >= 0) {
            BancoTemporario.listaUsuarios[index] = usuario
            return true
        }
        return false
    }

    suspend fun deletarUsuario(id: Int): Boolean {
        val usuario = BancoTemporario.listaUsuarios.find { it.id == id }
        if (usuario != null) {
            BancoTemporario.listaUsuarios.remove(usuario)
            return true
        }
        return false
    }

    // Adicionar estas novas funções para suporte ao Supabase
    suspend fun obterUsuario(id: String): Usuario? {
        return obterUsuario(id.toIntOrNull() ?: return null)
    }

    suspend fun criarUsuario(usuario: Usuario): Boolean {
        BancoTemporario.listaUsuarios.add(usuario)
        return true
    }

    suspend fun criarUsuario(dto: UsuarioDTO): Boolean {
        val usuario = Usuario(
            nome = dto.nome,
            email = dto.email,
            peso = dto.peso,
            altura = dto.altura
        )
        return criarUsuario(usuario)
    }

    suspend fun atualizarUsuario(dto: UsuarioDTO): Boolean {
        val id = dto.id?.toIntOrNull() ?: return false
        val usuarioExistente = obterUsuario(id) ?: return false

        val usuarioAtualizado = Usuario(
            nome = dto.nome,
            email = dto.email,
            peso = dto.peso,
            altura = dto.altura,
            id = id
        )

        return atualizarUsuario(usuarioAtualizado)
    }

    suspend fun deletarUsuario(id: String): Boolean {
        return deletarUsuario(id.toIntOrNull() ?: return false)
    }
}