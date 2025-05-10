package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast // <-- Add this import
import androidx.appcompat.app.AppCompatActivity

class ExercicioFicha : AppCompatActivity() {

    private lateinit var categoriaSelecionada: String
    private lateinit var diaSelecionado: String
    private lateinit var listaExerciciosFiltrada: List<Exercicio>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercicio_ficha)

        // Recebe dados da tela anterior
        categoriaSelecionada = intent.getStringExtra("categoriaSelecionada") ?: ""
        diaSelecionado = intent.getStringExtra("diaSelecionado") ?: ""

        // Atualiza o título com a categoria selecionada
        val textViewTituloExercicios = findViewById<TextView>(R.id.textViewTituloExercicios)
        textViewTituloExercicios.text = "Selecione um exercício da categoria $categoriaSelecionada"

        // Filtra os exercícios por categoria
        listaExerciciosFiltrada = BancoTemporario.listaExercicios.filter {
            it.categoria.equals(categoriaSelecionada, ignoreCase = true)
        }

        // Botão de voltar
        findViewById<ImageButton>(R.id.imageButtonBack).setOnClickListener {
            finish()
        }

        // Dynamically create CheckBoxes for each exercise
        val layoutExercicios = findViewById<LinearLayout>(R.id.layoutExercicios)
        listaExerciciosFiltrada.forEach { exercicio ->
            val checkBox = CheckBox(this)
            checkBox.text = exercicio.nome
            checkBox.id = exercicio.id // Use the exercise ID as the checkbox ID
            layoutExercicios.addView(checkBox)
        }

        // Handle the "Continuar" button to proceed with the selected exercises
        findViewById<TextView>(R.id.buttonContinuar).setOnClickListener {
            val selectedExercises = mutableListOf<Int>()
            for (i in 0 until layoutExercicios.childCount) {
                val checkBox = layoutExercicios.getChildAt(i) as CheckBox
                if (checkBox.isChecked) {
                    selectedExercises.add(checkBox.id) // Get the ID of selected exercises
                }
            }

            if (selectedExercises.isNotEmpty()) {
                val intent = Intent(this, ConfigurarExercicioFicha::class.java)
                intent.putExtra("diaSelecionado", diaSelecionado)
                intent.putIntegerArrayListExtra("selectedExercises", ArrayList(selectedExercises)) // Send selected exercises IDs
                startActivity(intent)
            } else {
                // Handle case where no exercises are selected
                Toast.makeText(this, "Selecione pelo menos um exercício", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
