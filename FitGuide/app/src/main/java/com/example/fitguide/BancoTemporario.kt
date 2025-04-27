package com.example.fitguide



import kotlin.collections.remove

object BancoTemporario {
    val listaExercicios = mutableListOf<Exercicio>()

    fun deleteExercicio(exercicio: Exercicio) {
        listaExercicios.remove(exercicio)
    }
}