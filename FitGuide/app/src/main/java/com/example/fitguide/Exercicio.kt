package com.example.fitguide

data class Exercicio(
    var nome: String,
    var equipamento: String,
    var descricaoTecnica: String,
    var musculosTrabalhados: String,
    var linkVideo: String,
    val categoria: String,
    val id: Int = nextId++,
    val equipamentoId: String? = null  // Adicionando esta propriedade
) {
    companion object {
        private var nextId = 1
    }
}