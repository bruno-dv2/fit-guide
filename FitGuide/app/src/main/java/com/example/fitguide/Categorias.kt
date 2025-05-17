package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitguide.databinding.ActivityCategoriasBinding
import com.example.fitguide.repositories.ExercicioRepository
import kotlinx.coroutines.launch

class Categorias : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriasBinding
    private lateinit var categoria: String
    private var origemAluno = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoria = intent.getStringExtra("categoria")
            ?: throw IllegalArgumentException("Categoria deve ser passada para Categorias")

        origemAluno = intent.getBooleanExtra("origemAluno", false)

        binding.textViewTitle.text = "Exercícios da Categoria: $categoria"

        binding.imageButtonBack.setOnClickListener {
            finish()
        }

        binding.buttonAdicionarExercicio.setOnClickListener {
            val intent = Intent(this, CriarExercicio::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        // Esconder botão de adicionar para alunos
        if (origemAluno) {
            binding.buttonAdicionarExercicio.visibility = View.GONE
        }

        carregarExercicios()
    }

    override fun onResume() {
        super.onResume()
        carregarExercicios()
    }

    private fun carregarExercicios() {
        binding.linearLayoutExercises.removeAllViews()

        lifecycleScope.launch {
            val exercicios = ExercicioRepository.listarExerciciosPorCategoria(categoria)

            if (exercicios.isEmpty()) {
                binding.textViewNoExercises.visibility = View.VISIBLE
                binding.textViewNoExercises.text = "Nenhum exercício encontrado para essa categoria"
            } else {
                binding.textViewNoExercises.visibility = View.GONE

                for (exercicio in exercicios) {
                    val exercicioView = layoutInflater.inflate(
                        R.layout.item_exercicio_lista,
                        binding.linearLayoutExercises,
                        false
                    )

                    val nomeTextView = exercicioView.findViewById<TextView>(R.id.textViewNomeExercicio)
                    val detalhesTextView = exercicioView.findViewById<TextView>(R.id.textViewDetalhesExercicio)
                    val editarTextView = exercicioView.findViewById<TextView>(R.id.textViewEditarExercicio)

                    nomeTextView.text = exercicio.nome
                    detalhesTextView.text = "Equipamento: ${exercicio.equipamento}"

                    if (origemAluno) {
                        editarTextView.visibility = View.GONE
                    } else {
                        editarTextView.setOnClickListener {
                            val intent = Intent(this@Categorias, EditarExercicio::class.java)
                            intent.putExtra("exercicioId", exercicio.id)
                            startActivity(intent)
                        }
                    }

                    binding.linearLayoutExercises.addView(exercicioView)
                }
            }
        }
    }
}