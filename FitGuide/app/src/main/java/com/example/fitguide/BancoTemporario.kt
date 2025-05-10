package com.example.fitguide

import kotlin.collections.remove

object BancoTemporario {
    val listaExercicios = mutableListOf<Exercicio>()
    val listaEquipamentos = mutableListOf<Equipamento>()
    val listaFichas = mutableListOf<Ficha>()
    val listaUsuarios = mutableListOf<Usuario>()

    fun deleteExercicio(exercicio: Exercicio) {
        listaExercicios.remove(exercicio)
    }

    fun deleteEquipamento(equipamento: Equipamento) {
        listaEquipamentos.remove(equipamento)
    }

    fun deleteFicha(ficha: Ficha) {
        listaFichas.remove(ficha)
    }

    fun deleteUsuario(usuario: Usuario) {
        listaUsuarios.remove(usuario)
    }
}