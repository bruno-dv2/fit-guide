package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityCriarFichaBinding

class CriarFicha : AppCompatActivity() {

    private lateinit var binding: ActivityCriarFichaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarFichaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButtonBack.setOnClickListener {
            finish()
        }

        binding.textViewTitle.text = "Nome da Ficha"
        binding.textViewSubtitle.text = "Programe cada dia da semana"

        binding.buttonSalvar.setOnClickListener {
            val nomeFicha = binding.editTextNomeFicha.text.toString()
            if (nomeFicha.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("newFicha", nomeFicha)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        val dias = listOf("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira")
        val botoesDias = listOf(
            binding.buttonSegunda,
            binding.buttonTerca,
            binding.buttonQuarta,
            binding.buttonQuinta,
            binding.buttonSexta
        )

        for ((indice, dia) in dias.withIndex()) {
            botoesDias[indice].text = dia
            botoesDias[indice].setOnClickListener {
                val nomeFicha = binding.editTextNomeFicha.text.toString()
                val intent = Intent(this, CategoriaFicha::class.java)
                intent.putExtra("diaSelecionado", dia)
                intent.putExtra("nomeFicha", nomeFicha)
                startActivity(intent)
            }
        }
    }
}
