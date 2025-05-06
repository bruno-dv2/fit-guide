package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.fitguide.databinding.ActivityMeuTreinoBinding
import java.util.*

class MeuTreino : AppCompatActivity() {

    private lateinit var binding: ActivityMeuTreinoBinding
    private val exercicios = mutableListOf<Pair<String, String>>()
    private var diaSelecionado: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeuTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButtonBack.setOnClickListener {
            finish()
        }

        exibirDiasDaSemana(diaSelecionado)
        val tipo = obterTipoTreino(diaSelecionado)
        atualizarTreino(tipo, diaSelecionado)

        binding.buttonFinalizar.setOnClickListener {
            TreinoConcluidoStorage.marcarComoConcluido(diaSelecionado)

            val resultIntent = Intent()
            resultIntent.putExtra("diaFinalizado", diaSelecionado)
            setResult(RESULT_OK, resultIntent)

            Toast.makeText(this, "Treino finalizado!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun exibirDiasDaSemana(diaAtual: Int) {
        val layoutDias = binding.layoutDiasSemana
        layoutDias.removeAllViews()

        val dias = listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB")

        for ((index, nomeDia) in dias.withIndex()) {
            val button = Button(this).apply {
                text = nomeDia
                setPadding(24, 8, 24, 8)
                setTextColor(resources.getColor(android.R.color.white))
                setBackgroundColor(
                    if (index + 1 == diaAtual) resources.getColor(android.R.color.holo_blue_dark)
                    else resources.getColor(android.R.color.darker_gray)
                )

                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 0, 8, 0)
                }

                setOnClickListener {
                    diaSelecionado = index + 1
                    exibirDiasDaSemana(diaSelecionado)
                    val tipo = obterTipoTreino(diaSelecionado)
                    atualizarTreino(tipo, diaSelecionado)
                }
            }

            layoutDias.addView(button)
        }
    }

    private fun obterTipoTreino(dia: Int): String {
        return when (dia) {
            Calendar.MONDAY -> "Braços"
            Calendar.TUESDAY -> "Pernas"
            Calendar.WEDNESDAY -> "Peito"
            Calendar.THURSDAY -> "Braços"
            Calendar.FRIDAY -> "Peito"
            Calendar.SATURDAY, Calendar.SUNDAY -> "Descanso"
            else -> "Indefinido"
        }
    }

    private fun atualizarTreino(tipoTreino: String, dia: Int) {
        val scrollContent = binding.scrollView.getChildAt(0) as LinearLayout
        scrollContent.removeAllViews()
        exercicios.clear()

        val nomeDia = when (dia) {
            Calendar.SUNDAY -> "Domingo"
            Calendar.MONDAY -> "Segunda-feira"
            Calendar.TUESDAY -> "Terça-feira"
            Calendar.WEDNESDAY -> "Quarta-feira"
            Calendar.THURSDAY -> "Quinta-feira"
            Calendar.FRIDAY -> "Sexta-feira"
            Calendar.SATURDAY -> "Sábado"
            else -> "Dia"
        }

        if (tipoTreino == "Descanso") {
            binding.textViewDiaAtual.text = "$nomeDia: Dia de Descanso"
            val descansoText = TextView(this)
            descansoText.text = "Hoje é dia de descanso. Aproveite para recuperar os músculos!"
            descansoText.textSize = 16f
            descansoText.setPadding(16, 32, 16, 0)
            scrollContent.addView(descansoText)
            return
        }

        binding.textViewDiaAtual.text = "$nomeDia: Treino de $tipoTreino"

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
        }

        for (exercicio in exercicios) {
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.item_exercicio, null)

            val nome = view.findViewById<TextView>(R.id.textViewNomeExercicio)
            val detalhes = view.findViewById<TextView>(R.id.textViewDetalhesExercicio)
            val checkBox = view.findViewById<CheckBox>(R.id.checkBoxExercicio)
            val card = view.findViewById<CardView>(R.id.cardViewExercicio)

            nome.text = exercicio.first
            detalhes.text = exercicio.second

            card.setOnClickListener {
                val intent = Intent(this, DetalheExercicio::class.java)
                intent.putExtra("nomeExercicio", exercicio.first)
                startActivity(intent)
            }

            scrollContent.addView(view)
        }
    }
}
