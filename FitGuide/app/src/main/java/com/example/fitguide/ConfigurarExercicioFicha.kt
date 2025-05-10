package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.text.InputType
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConfigurarExercicioFicha : AppCompatActivity() {

    private lateinit var diaSelecionado: String
    private lateinit var selectedExercises: List<Int> // Lista de IDs dos exercícios selecionados

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configurar_exercicio_ficha)

        diaSelecionado = intent.getStringExtra("diaSelecionado") ?: ""
        selectedExercises = intent.getIntegerArrayListExtra("selectedExercises")?.toList() ?: emptyList()

        if (selectedExercises.isEmpty()) {
            Toast.makeText(this, "Nenhum exercício selecionado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val textViewSelectedExercises = findViewById<TextView>(R.id.textViewSelectedExercises)
        textViewSelectedExercises.text = "Exercícios selecionados"

        val layoutExercicios = findViewById<LinearLayout>(R.id.layoutExercicios)

        val exerciseInputs = mutableListOf<Pair<Int, Pair<String, String>>>() // To hold exercise ID with series/reps

        selectedExercises.forEach { id ->
            val exercise = BancoTemporario.listaExercicios.find { it.id == id }
            if (exercise != null) {
                val exerciseLayout = LinearLayout(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 16, 0, 0)
                    }
                    orientation = LinearLayout.VERTICAL
                }

                val nameAndInputs = LinearLayout(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    orientation = LinearLayout.HORIZONTAL
                }

                val exerciseName = TextView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                    text = exercise.nome
                    setTextColor(resources.getColor(android.R.color.black))
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                }

                val seriesInput = EditText(this).apply {
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f)
                    hint = "Séries"
                    inputType = InputType.TYPE_CLASS_NUMBER
                }

                val repInput = EditText(this).apply {
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f)
                    hint = "Repetições"
                    inputType = InputType.TYPE_CLASS_NUMBER
                }

                nameAndInputs.addView(exerciseName)
                nameAndInputs.addView(seriesInput)
                nameAndInputs.addView(repInput)

                exerciseLayout.addView(nameAndInputs)
                layoutExercicios.addView(exerciseLayout)

                // Save exercise ID with its respective series/reps input
                exerciseInputs.add(Pair(id, Pair(seriesInput.text.toString(), repInput.text.toString())))
            } else {
                Toast.makeText(this, "Exercício com ID $id não encontrado", Toast.LENGTH_SHORT).show()
            }
        }

        val backButton = findViewById<ImageButton>(R.id.imageButtonBack)
        backButton.setOnClickListener {
            finish()
        }

        val salvarButton = findViewById<Button>(R.id.buttonSalvarDiaTreino)
        salvarButton.setOnClickListener {
            val seriesAndReps = exerciseInputs.map { (id, pair) ->
                mapOf(
                    "exerciseId" to id,
                    "series" to pair.first,
                    "reps" to pair.second
                )
            }

            val intent = Intent(this, CriarFicha::class.java)
            intent.putExtra("diaSelecionado", diaSelecionado)
            intent.putExtra("exerciseData", ArrayList(seriesAndReps))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
