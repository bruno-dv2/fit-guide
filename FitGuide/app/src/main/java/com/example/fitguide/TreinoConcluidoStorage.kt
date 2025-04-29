package com.example.fitguide

object TreinoConcluidoStorage {
    private val treinosConcluidos = mutableSetOf<Int>()

    fun marcarComoConcluido(dia: Int) {
        treinosConcluidos.add(dia)
    }

    fun isConcluido(dia: Int): Boolean {
        return treinosConcluidos.contains(dia)
    }
}
