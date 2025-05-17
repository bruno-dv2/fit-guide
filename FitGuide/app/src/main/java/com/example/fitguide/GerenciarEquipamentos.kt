package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitguide.databinding.ActivityGerenciarEquipamentosBinding
import com.example.fitguide.repositories.EquipamentoRepository
import kotlinx.coroutines.launch

class GerenciarEquipamentos : AppCompatActivity() {

    private lateinit var binding: ActivityGerenciarEquipamentosBinding
    private var origemAluno = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerenciarEquipamentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        origemAluno = intent.getBooleanExtra("origemAluno", false)

        binding.imageButtonBack.setOnClickListener {
            // Navegar de volta para HomeProfessor ou HomeAluno
            if (origemAluno) {
                val intent = Intent(this, HomeAluno::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, HomeProfessor::class.java)
                startActivity(intent)
            }
            finish()
        }

        binding.buttonCriarEquipamento.setOnClickListener {
            val intent = Intent(this, CriarEquipamento::class.java)
            startActivity(intent)
        }

        carregarEquipamentos()
    }

    override fun onResume() {
        super.onResume()
        carregarEquipamentos()
    }

    private fun carregarEquipamentos() {
        binding.equipamentosContainer.removeAllViews()

        lifecycleScope.launch {
            val equipamentos = EquipamentoRepository.listarEquipamentos()

            if (equipamentos.isEmpty()) {
                binding.emptyMessage.visibility = View.VISIBLE
            } else {
                binding.emptyMessage.visibility = View.GONE

                for (equipamento in equipamentos) {
                    val itemView = layoutInflater.inflate(
                        R.layout.item_equipamento,
                        binding.equipamentosContainer,
                        false
                    )

                    val nomeTextView = itemView.findViewById<android.widget.TextView>(R.id.textViewNomeEquipamento)
                    val detalhesTextView = itemView.findViewById<android.widget.TextView>(R.id.textViewDetalhesEquipamento)
                    val editarButton = itemView.findViewById<android.widget.Button>(R.id.buttonEditarEquipamento)

                    nomeTextView.text = equipamento.nome
                    detalhesTextView.text = equipamento.descricaoTecnica


                    editarButton.setOnClickListener {
                        val intent = Intent(this@GerenciarEquipamentos, EditarEquipamento::class.java)
                        intent.putExtra("equipamentoId", equipamento.id)
                        startActivity(intent)
                    }

                    binding.equipamentosContainer.addView(itemView)
                }
            }
        }
    }
}