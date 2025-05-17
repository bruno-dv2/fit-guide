package com.example.fitguide

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitguide.databinding.ActivityCriarExercicioBinding
import com.example.fitguide.repositories.ExercicioRepository
import kotlinx.coroutines.launch

class CriarExercicio : AppCompatActivity() {

    private lateinit var binding: ActivityCriarExercicioBinding
    private lateinit var categoria: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarExercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoria = intent.getStringExtra("categoria")
            ?: throw IllegalArgumentException("Categoria tem que ser passada para CriarExercicio")

        binding.textViewTitle.text = "Criar Exercício - $categoria"

        binding.botaoSalvar.setOnClickListener {
            salvarExercicio()
        }
    }

    private fun salvarExercicio() {
        val nome = binding.campoNome.text.toString()
        val equipamento = binding.campoEquipamento.text.toString()
        val descricao = binding.campoDescricao.text.toString()
        val musculos = binding.campoMusculos.text.toString()
        val link = binding.campoLink.text.toString()

        if (nome.isBlank() || descricao.isBlank() || musculos.isBlank()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val exercicioDTO = ExercicioDTO(
                nome = nome,
                equipamento_id = null,  // Aqui você poderia buscar o ID do equipamento pelo nome
                descricao_tecnica = descricao,
                musculos_trabalhados = musculos,
                link_video = link,
                categoria = categoria
            )

            val sucesso = ExercicioRepository.criarExercicio(exercicioDTO)

            if (sucesso) {
                Toast.makeText(this@CriarExercicio, "Exercício criado com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@CriarExercicio, "Erro ao criar exercício", Toast.LENGTH_SHORT).show()
            }
        }
    }
}