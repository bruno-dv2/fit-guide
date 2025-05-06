package com.example.fitguide

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfigurarTreino : AppCompatActivity() {

    private var fichaId: Int = -1
    private lateinit var ficha: Ficha
    private lateinit var diaSemana: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configurar_treino)

        // Configurar o botão de voltar
        val botaoVoltar = findViewById<ImageButton>(R.id.imageButtonBack)
        botaoVoltar.setOnClickListener {
            finish()
        }

        // Obter o ID da ficha e dia da semana
        fichaId = intent.getIntExtra("fichaId", -1)
        diaSemana = intent.getStringExtra("diaSemana") ?: ""

        if (fichaId == -1 || diaSemana.isEmpty()) {
            finish()
            return
        }

        ficha = BancoTemporario.listaFichas.find { it.id == fichaId }
            ?: run {
                finish()
                return
            }

        // Atualizar título
        findViewById<TextView>(R.id.textViewTitulo).text = "Configure seu treino"
        findViewById<TextView>(R.id.textViewSubtitulo).text = "Configure séries e repetições"

        // Carregar exercícios selecionados
        carregarExercicios()

        // Configurar botão salvar
        findViewById<Button>(R.id.buttonSalvar).setOnClickListener {
            salvarConfiguracoes()

            // Verificar se existem mais dias selecionados não configurados
            val proximoDia = encontrarProximoDiaSemConfiguration()
            if (proximoDia != null) {
                // Abrir seleção de grupo muscular para o próximo dia
                val intent = android.content.Intent(this, SelecionarGrupoMuscular::class.java)
                intent.putExtra("fichaId", fichaId)
                intent.putExtra("diaSemana", proximoDia)
                startActivity(intent)
            } else {
                // Concluiu a configuração de todos os dias
                Toast.makeText(this, "Ficha configurada com sucesso!", Toast.LENGTH_SHORT).show()
                finalizarConfiguracao()
            }
        }
    }

    private fun carregarExercicios() {
        val exerciciosContainer = findViewById<LinearLayout>(R.id.exerciciosContainer)
        exerciciosContainer.removeAllViews()

        val diaTreino = ficha.diasTreino[diaSemana] ?: return
        val exercicios = diaTreino.exercicios

        for (exercicioTreino in exercicios) {
            val exercicioView = LayoutInflater.from(this).inflate(R.layout.item_configurar_exercicio, exerciciosContainer, false)

            val nomeExercicio = exercicioView.findViewById<TextView>(R.id.textViewNomeExercicio)
            val editTextSeries = exercicioView.findViewById<EditText>(R.id.editTextSeries)
            val editTextRepeticoes = exercicioView.findViewById<EditText>(R.id.editTextRepeticoes)

            nomeExercicio.text = exercicioTreino.exercicio.nome
            editTextSeries.setText(exercicioTreino.series.toString())
            editTextRepeticoes.setText(exercicioTreino.repeticoes.toString())

            // Salvar referência ao exercício na tag da view
            exercicioView.tag = exercicioTreino

            exerciciosContainer.addView(exercicioView)
        }
    }

    private fun salvarConfiguracoes() {
        val exerciciosContainer = findViewById<LinearLayout>(R.id.exerciciosContainer)

        for (i in 0 until exerciciosContainer.childCount) {
            val exercicioView = exerciciosContainer.getChildAt(i)
            val exercicioTreino = exercicioView.tag as ExercicioTreino

            val editTextSeries = exercicioView.findViewById<EditText>(R.id.editTextSeries)
            val editTextRepeticoes = exercicioView.findViewById<EditText>(R.id.editTextRepeticoes)

            exercicioTreino.series = editTextSeries.text.toString().toIntOrNull() ?: 4
            exercicioTreino.repeticoes = editTextRepeticoes.text.toString().toIntOrNull() ?: 12
        }

        Toast.makeText(this, "Configurações salvas para $diaSemana", Toast.LENGTH_SHORT).show()
    }

    private fun encontrarProximoDiaSemConfiguration(): String? {
        // Dias da semana em ordem
        val diasOrdenados = listOf("SEGUNDA-FEIRA", "TERÇA-FEIRA", "QUARTA-FEIRA", "QUINTA-FEIRA", "SEXTA-FEIRA")

        // Encontrar o próximo dia após o atual que está na ficha
        val indexAtual = diasOrdenados.indexOf(diaSemana)
        if (indexAtual >= 0) {
            for (i in indexAtual + 1 until diasOrdenados.size) {
                val dia = diasOrdenados[i]
                if (ficha.diasTreino.containsKey(dia) && ficha.diasTreino[dia]?.exercicios?.isEmpty() == true) {
                    return dia
                }
            }
        }

        // Se não encontrou depois do atual, verificar todos desde o início
        for (dia in diasOrdenados) {
            if (dia != diaSemana && ficha.diasTreino.containsKey(dia) && ficha.diasTreino[dia]?.exercicios?.isEmpty() == true) {
                return dia
            }
        }

        return null // Não há mais dias para configurar
    }

    private fun finalizarConfiguracao() {
        // Voltar para a tela de gerenciamento de fichas
        val intent = android.content.Intent(this, GerenciarFichas::class.java)
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}