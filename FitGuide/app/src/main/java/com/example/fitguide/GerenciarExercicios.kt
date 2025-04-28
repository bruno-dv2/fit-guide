package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityGerenciarExerciciosBinding

class GerenciarExercicios : AppCompatActivity() {

    private lateinit var binding: ActivityGerenciarExerciciosBinding
    private var origemAluno = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerenciarExerciciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        origemAluno = intent.getBooleanExtra("origemAluno", false)

        // Configurar o botão de voltar
        val botaoVoltar: ImageButton = binding.imageButtonBack
        botaoVoltar.setOnClickListener {
            // Navegar de volta para HomeProfessor ou HomeAluno
            if (origemAluno) {
                val intent = Intent(this, HomeAluno::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, HomeProfessor::class.java)
                startActivity(intent)
            }
            finish() // Fechar a atividade atual
        }

        // Configurar os listeners de clique para os botões de categoria muscular
        binding.buttonPeito.setOnClickListener {
            iniciarCategoria("Peito")
        }

        binding.buttonCostas.setOnClickListener {
            iniciarCategoria("Costas")
        }

        binding.buttonOmbro.setOnClickListener {
            iniciarCategoria("Ombro")
        }

        binding.buttonPernas.setOnClickListener {
            iniciarCategoria("Pernas")
        }

        binding.buttonBracos.setOnClickListener {
            iniciarCategoria("Braços")
        }

        binding.buttonAbdomen.setOnClickListener {
            iniciarCategoria("Abdômen")
        }
    }

    private fun iniciarCategoria(categoria: String) {
        val intent = Intent(this, Categorias::class.java)
        intent.putExtra("categoria", categoria)
        intent.putExtra("origemAluno", origemAluno)
        startActivity(intent)
    }
}