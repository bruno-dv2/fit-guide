package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityEditarFichaBinding

class EditarFicha : AppCompatActivity() {

    private lateinit var binding: ActivityEditarFichaBinding  // Use the new binding class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarFichaBinding.inflate(layoutInflater)  // Inflate the correct layout
        setContentView(binding.root)

        binding.textViewTitle.text = "Nome da Ficha (Edição)"
        binding.textViewSubtitle.text = "Programe cada dia da semana"

        val fichaNome = intent.getStringExtra("fichaParaEditar")
        fichaNome?.let {
            binding.editTextNomeFicha.setText(it)
        }

        binding.imageButtonBack.setOnClickListener {
            finish()
        }

        binding.buttonSalvar.setOnClickListener {
            val nomeFicha = binding.editTextNomeFicha.text.toString()
            if (nomeFicha.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("updatedFicha", nomeFicha)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        binding.buttonDelete.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("deletedFicha", fichaNome)  // Send the name of the ficha to be deleted
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        val dias = listOf("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira")
        val botoesDias = listOf(
            binding.buttonSegunda,
            binding.buttonTerca,
            binding.buttonQuarta,
            binding.buttonQuinta,
            binding.buttonSexta
        )

        for ((i, dia) in dias.withIndex()) {
            botoesDias[i].text = dia
            botoesDias[i].setOnClickListener {
                val intent = Intent(this, CategoriaFicha::class.java)
                intent.putExtra("diaSelecionado", dia)
                startActivity(intent)
            }
        }
    }
}
