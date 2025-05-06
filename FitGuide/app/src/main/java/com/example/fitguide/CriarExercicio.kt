package com.example.fitguide


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityCriarExercicioBinding

class CriarExercicio : AppCompatActivity() {

    private lateinit var binding: ActivityCriarExercicioBinding
    private lateinit var categoria: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarExercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the category name from the Intent
        categoria = intent.getStringExtra("categoria")
            ?: throw IllegalArgumentException("Categoria tem que ser passada para CriarExercicio")

        // Set the title
        binding.textViewTitle.text = "Criar Exercício - $categoria"

        val botaoSalvar: Button = binding.botaoSalvar
        botaoSalvar.setOnClickListener {
            val exercicio = Exercicio(
                nome = binding.campoNome.text.toString(),
                equipamento = binding.campoEquipamento.text.toString(),
                descricaoTecnica = binding.campoDescricao.text.toString(),
                musculosTrabalhados = binding.campoMusculos.text.toString(),
                linkVideo = binding.campoLink.text.toString(),
                categoria = categoria // Save the category
            )
            BancoTemporario.listaExercicios.add(exercicio)
            finish() // volta pra activity anterior
        }
    }
}