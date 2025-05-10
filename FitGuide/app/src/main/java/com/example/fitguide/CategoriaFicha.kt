package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CategoriaFicha : AppCompatActivity() {

    private lateinit var diaSelecionado: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria_ficha)

        diaSelecionado = intent.getStringExtra("diaSelecionado") ?: ""

        findViewById<ImageButton>(R.id.imageButtonBack).setOnClickListener {
            finish()
        }


        val botoesCategorias = listOf(
            R.id.buttonPeito to "Peito",
            R.id.buttonCostas to "Costas",
            R.id.buttonOmbro to "Ombro",
            R.id.buttonPernas to "Pernas",
            R.id.buttonBracos to "Braços",
            R.id.buttonAbdomen to "Abdômen"
        )

        // ação de clicar em qualquer categoria
        botoesCategorias.forEach { (id, categoria) ->
            findViewById<Button>(id).setOnClickListener {
                val intent = Intent(this, ExercicioFicha::class.java)
                intent.putExtra("categoriaSelecionada", categoria)
                intent.putExtra("diaSelecionado", diaSelecionado)
                startActivity(intent)
            }
        }
    }
}
