package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityCategoriasBinding

class Categorias : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriasBinding
    private lateinit var listaExerciciosView: LinearLayout
    private lateinit var textoStatus: TextView
    private lateinit var categoria: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaExerciciosView = binding.linearLayoutExercises
        textoStatus = binding.textViewNoExercises

        // Obter o nome da categoria da Intent
        categoria = intent.getStringExtra("categoria")
            ?: throw IllegalArgumentException("Categoria deve ser passada para Categorias")

        // Definir o título da tela
        binding.textViewTitle.text = "Exercícios da Categoria: $categoria"

        // Configurar o botão de voltar
        val botaoVoltar: ImageButton = binding.imageButtonBack
        botaoVoltar.setOnClickListener {
            finish()
        }

        // Configurar o botão de adicionar exercício
        val botaoAdicionarExercicio: Button = binding.buttonAdicionarExercicio
        botaoAdicionarExercicio.setOnClickListener {
            val intent = Intent(this, CriarExercicio::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        atualizarLista()
    }

    private fun atualizarLista() {
        listaExerciciosView.removeAllViews()

        val exerciciosDaCategoria = BancoTemporario.listaExercicios.filter { it.categoria == categoria }

        if (exerciciosDaCategoria.isEmpty()) {
            textoStatus.visibility = View.VISIBLE
            textoStatus.text = "Nenhum exercício encontrado para essa categoria"
        } else {
            textoStatus.visibility = View.GONE
            for (exercicio in exerciciosDaCategoria) {
                val exercicioView = TextView(this)
                exercicioView.text = "Nome: ${exercicio.nome}\nEquipamento: ${exercicio.equipamento}"
                exercicioView.setPadding(16, 16, 16, 0)
                listaExerciciosView.addView(exercicioView)

                val editarDeletarView = TextView(this)
                editarDeletarView.text = "editar/deletar"
                editarDeletarView.setPadding(16, 0, 16, 16)
                editarDeletarView.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                editarDeletarView.setOnClickListener {
                    val intent = Intent(this, EditarExercicio::class.java)
                    intent.putExtra("exercicioId", exercicio.id)
                    startActivity(intent)
                }
                listaExerciciosView.addView(editarDeletarView)
            }
        }
    }
}