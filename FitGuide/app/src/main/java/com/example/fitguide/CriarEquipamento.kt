package com.example.fitguide

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitguide.databinding.ActivityCriarEquipamentoBinding
import com.example.fitguide.repositories.EquipamentoRepository
import kotlinx.coroutines.launch

class CriarEquipamento : AppCompatActivity() {

    private lateinit var binding: ActivityCriarEquipamentoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarEquipamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoSalvar.setOnClickListener {
            val nome = binding.campoNome.text.toString()
            val descricao = binding.campoDescricao.text.toString()
            val imagem = binding.campoImagem.text.toString()

            if (nome.isBlank() || descricao.isBlank()) {
                Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                // Criar equipamento usando DTO (preparando para Supabase)
                val equipamentoDTO = EquipamentoDTO(
                    nome = nome,
                    descricao_tecnica = descricao,
                    imagem_url = imagem
                )

                val sucesso = EquipamentoRepository.criarEquipamento(equipamentoDTO)

                if (sucesso) {
                    Toast.makeText(this@CriarEquipamento, "Equipamento criado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@CriarEquipamento, "Erro ao criar equipamento", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}