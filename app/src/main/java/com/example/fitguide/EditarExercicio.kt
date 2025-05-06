package com.example.fitguide

import android.os.Bundle
import android.widget.Button
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
        val botaoSalvar: Button = binding.botaoSalvar
        botaoSalvar.setOnClickListener {
            // Atualizar os dados do exercício
            exercicio.nome = binding.campoNome.text.toString()
            exercicio.equipamento = binding.campoEquipamento.text.toString()
            exercicio.descricaoTecnica = binding.campoDescricao.text.toString()
            exercicio.musculosTrabalhados = binding.campoMusculos.text.toString()
            exercicio.linkVideo = binding.campoLink.text.toString()

            finish()
        }

        // Configurar o botão de deletar
        val botaoDeletar: Button = binding.botaoDeletar
        botaoDeletar.setOnClickListener {
            BancoTemporario.deleteExercicio(exercicio)
            finish()
        }
    }
}