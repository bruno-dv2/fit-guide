package com.example.fitguide

data class Equipamento(
    var nome: String,
    var descricaoTecnica: String,
    var imagem: String, // depois trocar o valor pra imagem de verdade
    val id: Int = nextId++
) {
    companion object {
        private var nextId = 1
    }
}