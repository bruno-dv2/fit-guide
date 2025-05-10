package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityAtribuirFichaBinding

class AtribuirFicha : AppCompatActivity() {

    private lateinit var binding: ActivityAtribuirFichaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtribuirFichaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
        binding.imageButtonBack.setOnClickListener {
            startActivity(Intent(this, DetalheUsuario::class.java))
            finish()
        }

        // Example Fichas (you should pull these from your database or real list)
        val listaFichas = listOf("Ficha A", "Ficha B", "Ficha C")

        for (ficha in listaFichas) {
            val fichaView = TextView(this).apply {
                text = ficha
                textSize = 20f
                setPadding(24, 32, 24, 32)
                setTextColor(resources.getColor(android.R.color.black))
                setOnClickListener {
                    Toast.makeText(this@AtribuirFicha, "$ficha atribuída com sucesso", Toast.LENGTH_SHORT).show()
                }
            }
            binding.containerFichas.addView(fichaView)
        }

        // Back button functionality
        binding.imageButtonBack.setOnClickListener {
            startActivity(Intent(this, GerenciarUsuarios::class.java))
            finish()
        }
    }
}
