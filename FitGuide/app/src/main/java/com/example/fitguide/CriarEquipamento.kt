package com.example.fitguide

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityCriarEquipamentoBinding

class CriarEquipamento : AppCompatActivity() {

    private lateinit var binding: ActivityCriarEquipamentoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarEquipamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botaoSalvar: Button = binding.botaoSalvar
        botaoSalvar.setOnClickListener {
            val equipamento = Equipamento(
                nome = binding.campoNome.text.toString(),
                descricaoTecnica = binding.campoDescricao.text.toString(),
                imagem = binding.campoImagem.text.toString()
            )
            BancoTemporario.listaEquipamentos.add(equipamento)
            finish() // volta pra activity anterior
        }
    }
}