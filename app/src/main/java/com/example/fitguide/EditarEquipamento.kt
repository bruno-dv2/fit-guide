package com.example.fitguide

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityEditarEquipamentoBinding

class EditarEquipamento : AppCompatActivity() {

    private lateinit var binding: ActivityEditarEquipamentoBinding
    private var equipamentoId: Int = -1
    private lateinit var equipamento: Equipamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarEquipamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        equipamentoId = intent.getIntExtra("equipamentoId", -1)
        if (equipamentoId == -1) {
            throw IllegalArgumentException("equipamentoId deve ser passado para EditarEquipamento")
        }

        equipamento = BancoTemporario.listaEquipamentos.find { it.id == equipamentoId }
            ?: throw IllegalArgumentException("Equipamento não encontrado com ID: $equipamentoId")

        // Preencher os campos com os dados do equipamento
        binding.campoNome.setText(equipamento.nome)
        binding.campoDescricao.setText(equipamento.descricaoTecnica)
        binding.campoImagem.setText(equipamento.imagem)

        // Configurar o botão de salvar
        val botaoSalvar: Button = binding.botaoSalvar
        botaoSalvar.setOnClickListener {
            // Atualizar os dados do equipamento
            equipamento.nome = binding.campoNome.text.toString()
            equipamento.descricaoTecnica = binding.campoDescricao.text.toString()
            equipamento.imagem = binding.campoImagem.text.toString()

            finish()
        }

        // Configurar o botão de deletar
        val botaoDeletar: Button = binding.botaoDeletar
        botaoDeletar.setOnClickListener {
            BancoTemporario.deleteEquipamento(equipamento)
            finish()
        }
    }
}