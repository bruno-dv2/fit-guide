package com.example.fitguide

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityDetalheExercicioBinding
import com.google.android.material.chip.Chip

class DetalheExercicio : AppCompatActivity() {
    private lateinit var binding: ActivityDetalheExercicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheExercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obter o nome do exercício passado pela Intent
        val nomeExercicio = intent.getStringExtra("nomeExercicio") ?: "Exercício"

        // Configurar o título da tela
        binding.textViewTitulo.text = nomeExercicio

        // Configurar a descrição técnica e músculos trabalhados com base no exercício
        configurarDetalhesExercicio(nomeExercicio)

        // Configurar o botão de voltar
        val botaoVoltar: ImageButton = binding.imageButtonBack
        botaoVoltar.setOnClickListener {
            finish() // Voltar para a tela anterior
        }

        // Configurar o botão principal de voltar
        binding.buttonVoltar.setOnClickListener {
            finish() // Voltar para a tela anterior
        }
    }

    private fun configurarDetalhesExercicio(nomeExercicio: String) {
        // Definir descrição técnica e músculos trabalhados com base no nome do exercício
        when (nomeExercicio) {
            "Supino Reto" -> {
                binding.textViewDescricaoTecnica.text = "Deite-se de costas em um banco plano. Segure a barra com as mãos um pouco mais afastadas que a largura dos ombros. Abaixe a barra lentamente até tocar levemente o meio do peito. Empurre a barra para cima até estender completamente os braços."
                // Mostrar os chips relevantes
                binding.chipPeitoral.visibility = View.VISIBLE
                binding.chipTriceps.visibility = View.VISIBLE
                binding.chipOmbro.visibility = View.VISIBLE
            }
            "Supino Inclinado" -> {
                binding.textViewDescricaoTecnica.text = "Deite-se em um banco inclinado (30-45 graus). Segure a barra com as mãos um pouco mais afastadas que a largura dos ombros. Abaixe a barra até tocar levemente a parte superior do peito. Empurre a barra para cima até estender completamente os braços."
                // Mostrar os chips relevantes
                binding.chipPeitoral.visibility = View.VISIBLE
                binding.chipTriceps.visibility = View.VISIBLE
                binding.chipOmbro.visibility = View.VISIBLE
            }
            "Crucifixo" -> {
                binding.textViewDescricaoTecnica.text = "Deite-se de costas em um banco plano. Segure um haltere em cada mão com os braços estendidos acima do peito, palmas das mãos viradas uma para a outra. Abaixe os braços para os lados em um movimento de arco, mantendo uma leve flexão nos cotovelos. Retorne à posição inicial."
                // Mostrar os chips relevantes
                binding.chipPeitoral.visibility = View.VISIBLE
                binding.chipOmbro.visibility = View.VISIBLE
                binding.chipTriceps.visibility = View.GONE
            }
            // Adicione mais exercícios conforme necessário
            else -> {
                binding.textViewDescricaoTecnica.text = "Descrição não disponível para este exercício."
                // Ocultar todos os chips
                binding.chipPeitoral.visibility = View.GONE
                binding.chipTriceps.visibility = View.GONE
                binding.chipOmbro.visibility = View.GONE
            }
        }
    }
}