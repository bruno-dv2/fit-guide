package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityGerenciarEquipamentosBinding

class GerenciarEquipamentos : AppCompatActivity() {

    private lateinit var binding: ActivityGerenciarEquipamentosBinding
    private var origemAluno = false
    private lateinit var equipamentosContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerenciarEquipamentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        equipamentosContainer = binding.equipamentosContainer
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

        // Configurar o botão de criar equipamento
        val botaoCriarEquipamento: Button = binding.buttonCriarEquipamento
        botaoCriarEquipamento.setOnClickListener {
            val intent = Intent(this, CriarEquipamento::class.java)
            startActivity(intent)
        }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val listaEquipamentos = BancoTemporario.listaEquipamentos
        equipamentosContainer.removeAllViews() // Clear existing views

        if (listaEquipamentos.isEmpty()) {
            binding.emptyMessage.visibility = View.VISIBLE
        } else {
            binding.emptyMessage.visibility = View.GONE
            for (equipamento in listaEquipamentos) {
                val equipamentoView = TextView(this)
                equipamentoView.text = "Nome: ${equipamento.nome}\nDetalhes: ${equipamento.descricaoTecnica}"
                equipamentoView.setPadding(16, 16, 16, 0)
                equipamentosContainer.addView(equipamentoView)

                val editarDeletarView = TextView(this)
                editarDeletarView.text = "editar/deletar"
                editarDeletarView.setPadding(16, 0, 16, 16)
                editarDeletarView.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                editarDeletarView.setOnClickListener {
                    val intent = Intent(this, EditarEquipamento::class.java)
                    intent.putExtra("equipamentoId", equipamento.id)
                    startActivity(intent)
                }
                equipamentosContainer.addView(editarDeletarView)
            }
        }
    }
}