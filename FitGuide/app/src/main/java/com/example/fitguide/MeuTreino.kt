package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.fitguide.databinding.ActivityMeuTreinoBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MeuTreino : AppCompatActivity() {
    private lateinit var binding: ActivityMeuTreinoBinding
    private val exercicios = mutableListOf<Pair<String, String>>() // Nome do exercício, Detalhes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeuTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exibirDiasDaSemana()


        // Configurar o botão de voltar
        val botaoVoltar: ImageButton = binding.imageButtonBack
        botaoVoltar.setOnClickListener {
            finish() // Voltar para a tela anterior
        }

        // Atualizar o dia atual e o tipo de treino
        val tipoTreino = atualizarDiaETreino()

        // Carregar os exercícios com base no tipo de treino
        carregarExercicios(tipoTreino)

        // Configurar o botão de finalizar treino
        binding.buttonFinalizar.setOnClickListener {
            Toast.makeText(this, "Treino finalizado com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Voltar para a tela anterior
        }
    }

    private fun exibirDiasDaSemana() {
        val layoutDias = binding.layoutDiasSemana
        val dias = listOf("S", "T", "Q", "Q", "S", "S", "D")
        val hoje = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) // 1 = Domingo, 2 = Segunda...

        for ((index, letra) in dias.withIndex()) {
            val textView = TextView(this)
            textView.text = letra
            textView.textSize = 18f
            textView.setPadding(16, 8, 16, 8)
            textView.setTextColor(if ((index + 1) == hoje) resources.getColor(android.R.color.white) else resources.getColor(android.R.color.black))
            textView.setBackgroundColor(if ((index + 1) == hoje) resources.getColor(android.R.color.darker_gray) else android.graphics.Color.TRANSPARENT)
            textView.setTypeface(null, android.graphics.Typeface.BOLD)

            layoutDias.addView(textView)
        }
    }


    private fun atualizarDiaETreino(): String {
        // Obter o dia atual da semana
        val calendar = Calendar.getInstance()
        val diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK)

        // Formatar o nome do dia da semana
        val formatoDia = SimpleDateFormat("EEEE", Locale("pt", "BR"))
        val nomeDoDia = formatoDia.format(calendar.time).capitalize(Locale.ROOT)

        // Definir o tipo de treino com base no dia da semana
        val tipoTreino = when (diaDaSemana) {
            Calendar.MONDAY -> "Braços"
            Calendar.TUESDAY -> "Pernas"
            Calendar.WEDNESDAY -> "Peito"
            Calendar.THURSDAY -> "Braços"
            Calendar.FRIDAY -> "Peito"
            Calendar.SATURDAY, Calendar.SUNDAY -> "Descanso"
            else -> "Indefinido"
        }

        // Atualizar o texto na interface do usuário
        if (tipoTreino == "Descanso") {
            binding.textViewDiaAtual.text = "$nomeDoDia: Dia de Descanso"
        } else {
            binding.textViewDiaAtual.text = "$nomeDoDia: Treino de $tipoTreino"
        }

        return tipoTreino
    }

    private fun carregarExercicios(tipoTreino: String) {
        val scrollContent = binding.scrollView.getChildAt(0) as LinearLayout
        scrollContent.removeAllViews()

        exercicios.clear()

        // Definir os exercícios com base no tipo de treino
        when (tipoTreino) {
            "Peito" -> {
                exercicios.add(Pair("Supino Reto", "4 séries x 12 repetições"))
                exercicios.add(Pair("Supino Inclinado", "4 séries x 12 repetições"))
                exercicios.add(Pair("Crucifixo", "4 séries x 12 repetições"))
            }
            "Braços" -> {
                exercicios.add(Pair("Rosca Direta", "4 séries x 12 repetições"))
                exercicios.add(Pair("Tríceps Corda", "4 séries x 12 repetições"))
                exercicios.add(Pair("Rosca Martelo", "4 séries x 12 repetições"))
            }
            "Pernas" -> {
                exercicios.add(Pair("Agachamento", "4 séries x 12 repetições"))
                exercicios.add(Pair("Leg Press", "4 séries x 12 repetições"))
                exercicios.add(Pair("Cadeira Extensora", "4 séries x 12 repetições"))
            }
            "Descanso" -> {
                // Não adicionar exercícios em dias de descanso
                val textView = TextView(this)
                textView.text = "Hoje é dia de descanso. Aproveite para recuperar os músculos!"
                textView.textSize = 16f
                textView.setPadding(0, 16, 0, 16)
                scrollContent.addView(textView)
                return
            }
        }

        // Adicionar os exercícios à interface
        for (exercicio in exercicios) {
            val inflater = LayoutInflater.from(this)
            val exercicioView = inflater.inflate(R.layout.item_exercicio, null)

            val nomeExercicio = exercicioView.findViewById<TextView>(R.id.textViewNomeExercicio)
            val detalhesExercicio = exercicioView.findViewById<TextView>(R.id.textViewDetalhesExercicio)
            val checkBox = exercicioView.findViewById<CheckBox>(R.id.checkBoxExercicio)
            val cardView = exercicioView.findViewById<CardView>(R.id.cardViewExercicio)

            nomeExercicio.text = exercicio.first
            detalhesExercicio.text = exercicio.second

            // Configurar clique no card para abrir tela de detalhes
            cardView.setOnClickListener {
                val intent = Intent(this, DetalheExercicio::class.java)
                intent.putExtra("nomeExercicio", exercicio.first)
                startActivity(intent)
            }

            scrollContent.addView(exercicioView)
        }
    }
}