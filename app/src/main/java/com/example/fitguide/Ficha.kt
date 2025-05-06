package com.example.fitguide

data class Ficha(
    var titulo: String,
    val diasTreino: MutableMap<String, DiaTreino> = mutableMapOf(),
    val id: Int = nextId++
) {
    companion object {
        private var nextId = 1
    }
}

data class DiaTreino(
    val diaSemana: String,
    val exercicios: MutableList<ExercicioTreino> = mutableListOf()
)

data class ExercicioTreino(
    val exercicio: Exercicio,
    var series: Int = 4,
    var repeticoes: Int = 12
)