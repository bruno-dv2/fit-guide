package com.example.fitguide

data class Usuario(
    var nome: String,
    var email: String,
    var peso: String,
    var altura: String,
    val id: Int = nextId++
) {
    companion object {
        private var nextId = 1
    }
}