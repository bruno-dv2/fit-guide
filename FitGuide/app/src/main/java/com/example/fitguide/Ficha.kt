package com.example.fitguide

data class Ficha(
    var titulo: String,
    var diasSelecionados: MutableList<String> = mutableListOf(),
    val id: Int = nextId++
) {
    companion object {
        private var nextId = 1
    }
}
