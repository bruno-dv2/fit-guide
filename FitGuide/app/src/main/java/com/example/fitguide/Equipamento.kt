package com.example.fitguide

data class Equipamento(
    var nome: String,
    var descricaoTecnica: String,
    var imagem: String, // You might want to change this to a more appropriate type for images (e.g., Uri, Int for drawable resource)
    val id: Int = nextId++
) {
    companion object {
        private var nextId = 1
    }
}