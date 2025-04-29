package com.example.fitguide

import kotlin.collections.remove

object BancoTemporario {
    val listaExercicios = mutableListOf<Exercicio>()
    val listaEquipamentos = mutableListOf<Equipamento>()

    fun deleteExercicio(exercicio: Exercicio) {
        listaExercicios.remove(exercicio)
    }

    fun deleteEquipamento(equipamento: Equipamento) {
        listaEquipamentos.remove(equipamento)
    }
}