package com.example.fitguide

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityEditarExercicioBinding

class EditarExercicio : AppCompatActivity() {

    private lateinit var binding: ActivityEditarExercicioBinding
    private var exercicioId: Int = -1
    private lateinit var exercicio: Exercicio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarExercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exercicioId = intent.getIntExtra("exercicioId", -1)
        if (exercicioId == -1) {
            throw IllegalArgumentException("exercicioId deve ser passado para EditarExercicio")
        }

        exercicio = BancoTemporario.listaExercicios.find { it.id == exercicioId }
            ?: throw IllegalArgumentException("Exercício não encontrado com ID: $exercicioId")

        // Preencher os campos com os dados do exercício
        binding.campoNome.setText(exercicio.nome)
        binding.campoEquipamento.setText(exercicio.equipamento)
        binding.campoDescricao.setText(exercicio.descricaoTecnica)
        binding.campoMusculos.setText(exercicio.musculosTrabalhados)
        binding.campoLink.setText(exercicio.linkVideo)

        // Configurar o botão de salvar
        binding.botaoSalvar.setOnClickListener {
            // Validar campos
            val nome = binding.campoNome.text.toString()
            val equipamento = binding.campoEquipamento.text.toString()
            val descricao = binding.campoDescricao.text.toString()
            val musculos = binding.campoMusculos.text.toString()
            val link = binding.campoLink.text.toString()

            if (nome.isBlank() || equipamento.isBlank() || descricao.isBlank() || musculos.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Atualizar os dados do exercício
            exercicio.nome = nome
            exercicio.equipamento = equipamento
            exercicio.descricaoTecnica = descricao
            exercicio.musculosTrabalhados = musculos
            exercicio.linkVideo = link

            Toast.makeText(this, "Exercício atualizado com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Configurar o botão de deletar
        binding.botaoDeletar.setOnClickListener {
            BancoTemporario.deleteExercicio(exercicio)
            Toast.makeText(this, "Exercício excluído com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}