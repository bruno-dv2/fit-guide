package com.example.fitguide

data class Mensagem(
    val texto: String,
    val tipo: TipoMensagem
)

enum class TipoMensagem {
    USUARIO,
    BOT
}
