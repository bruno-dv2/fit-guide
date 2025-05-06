package com.example.fitguide

object BancoTemporario {
    val listaExercicios = mutableListOf<Exercicio>()
    val listaEquipamentos = mutableListOf<Equipamento>()
    val listaUsuarios = mutableListOf<Usuario>()
    val listaFichas = mutableListOf<Ficha>()

    fun deleteExercicio(exercicio: Exercicio) {
        listaExercicios.remove(exercicio)
    }

    fun deleteEquipamento(equipamento: Equipamento) {
        listaEquipamentos.remove(equipamento)
    }

    fun deleteUsuario(usuario: Usuario) {
        listaUsuarios.remove(usuario)
    }

    fun deleteFicha(ficha: Ficha) {
        listaFichas.remove(ficha)
    }
}