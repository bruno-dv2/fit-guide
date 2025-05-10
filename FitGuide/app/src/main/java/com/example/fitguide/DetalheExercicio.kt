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

        //puxar nome pela intent
        val nomeExercicio = intent.getStringExtra("nomeExercicio") ?: "Exercício"

        binding.textViewTitulo.text = nomeExercicio

        configurarDetalhesExercicio(nomeExercicio)

        val botaoVoltar: ImageButton = binding.imageButtonBack
        botaoVoltar.setOnClickListener {
            finish()
        }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }

    private fun configurarDetalhesExercicio(nomeExercicio: String) {

        when (nomeExercicio) {
            "Supino Reto" -> {
                binding.textViewDescricaoTecnica.text = "Deite-se de costas em um banco plano. Segure a barra com as mãos um pouco mais afastadas que a largura dos ombros. Abaixe a barra lentamente até tocar levemente o meio do peito. Empurre a barra para cima até estender completamente os braços."
                binding.chipPeitoral.visibility = View.VISIBLE
                binding.chipTriceps.visibility = View.VISIBLE
                binding.chipOmbro.visibility = View.VISIBLE
            }
            "Supino Inclinado" -> {
                binding.textViewDescricaoTecnica.text = "Deite-se em um banco inclinado (30-45 graus). Segure a barra com as mãos um pouco mais afastadas que a largura dos ombros. Abaixe a barra até tocar levemente a parte superior do peito. Empurre a barra para cima até estender completamente os braços."
                binding.chipPeitoral.visibility = View.VISIBLE
                binding.chipTriceps.visibility = View.VISIBLE
                binding.chipOmbro.visibility = View.VISIBLE
            }
            "Crucifixo" -> {
                binding.textViewDescricaoTecnica.text = "Deite-se de costas em um banco plano. Segure um haltere em cada mão com os braços estendidos acima do peito, palmas das mãos viradas uma para a outra. Abaixe os braços para os lados em um movimento de arco, mantendo uma leve flexão nos cotovelos. Retorne à posição inicial."
                binding.chipPeitoral.visibility = View.VISIBLE
                binding.chipOmbro.visibility = View.VISIBLE
                binding.chipTriceps.visibility = View.GONE
            }
            else -> {
                binding.textViewDescricaoTecnica.text = "Descrição não disponível para este exercício."
                binding.chipPeitoral.visibility = View.GONE
                binding.chipTriceps.visibility = View.GONE
                binding.chipOmbro.visibility = View.GONE
            }
        }
    }
}