package com.example.fitguide

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitguide.databinding.ActivityEditarEquipamentoBinding
import com.example.fitguide.repositories.EquipamentoRepository
import kotlinx.coroutines.launch

class EditarEquipamento : AppCompatActivity() {

    private lateinit var binding: ActivityEditarEquipamentoBinding
    private var equipamentoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarEquipamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        equipamentoId = intent.getIntExtra("equipamentoId", -1)
        if (equipamentoId == -1) {
            Toast.makeText(this, "Equipamento não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        carregarEquipamento()

        binding.botaoSalvar.setOnClickListener {
            salvarEquipamento()
        }

        binding.botaoDeletar.setOnClickListener {
            confirmarExclusao()
        }
    }

    private fun carregarEquipamento() {
        lifecycleScope.launch {
            val equipamento = EquipamentoRepository.obterEquipamento(equipamentoId)

            if (equipamento == null) {
                Toast.makeText(this@EditarEquipamento, "Equipamento não encontrado", Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }

            binding.campoNome.setText(equipamento.nome)
            binding.campoDescricao.setText(equipamento.descricaoTecnica)
            binding.campoImagem.setText(equipamento.imagem)
        }
    }

    private fun salvarEquipamento() {
        val nome = binding.campoNome.text.toString()
        val descricao = binding.campoDescricao.text.toString()
        val imagem = binding.campoImagem.text.toString()

        if (nome.isBlank() || descricao.isBlank()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val equipamentoDTO = EquipamentoDTO(
                id = equipamentoId.toString(),
                nome = nome,
                descricao_tecnica = descricao,
                imagem_url = imagem
            )

            val sucesso = EquipamentoRepository.atualizarEquipamento(equipamentoDTO)

            if (sucesso) {
                Toast.makeText(this@EditarEquipamento, "Equipamento atualizado com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@EditarEquipamento, "Erro ao atualizar equipamento", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun confirmarExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Excluir Equipamento")
            .setMessage("Tem certeza que deseja excluir este equipamento?")
            .setPositiveButton("Sim") { _, _ ->
                excluirEquipamento()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun excluirEquipamento() {
        lifecycleScope.launch {
            val sucesso = EquipamentoRepository.deletarEquipamento(equipamentoId)

            if (sucesso) {
                Toast.makeText(this@EditarEquipamento, "Equipamento excluído com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@EditarEquipamento, "Erro ao excluir equipamento", Toast.LENGTH_SHORT).show()
            }
        }
    }
}