package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SelecionarGrupoMuscular : AppCompatActivity() {

    private var fichaId: Int = -1
    private lateinit var ficha: Ficha
    private lateinit var diaSemana: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecionar_grupo_muscular)

        // Configurar o botão de voltar
        val botaoVoltar = findViewById<ImageButton>(R.id.imageButtonBack)
        botaoVoltar.setOnClickListener {
            finish()
        }

        // Obter o ID da ficha e dia da semana
        fichaId = intent.getIntExtra("fichaId", -1)
        diaSemana = intent.getStringExtra("diaSemana") ?: ""

        if (fichaId == -1 || diaSemana.isEmpty()) {
            finish()
            return
        }

        ficha = BancoTemporario.listaFichas.find { it.id == fichaId }
            ?: run {
                finish()
                return
            }

        // Atualizar subtítulo
        findViewById<TextView>(R.id.textViewSubtitulo).text = "Escolha o grupo para treinar neste dia"

        // Configurar os botões de grupo muscular
        findViewById<CardView>(R.id.cardPeito).setOnClickListener {
            selecionarGrupoMuscular("Peito")
        }

        findViewById<CardView>(R.id.cardCostas).setOnClickListener {
            selecionarGrupoMuscular("Costas")
        }

        findViewById<CardView>(R.id.cardPernas).setOnClickListener {
            selecionarGrupoMuscular("Pernas")
        }

        findViewById<CardView>(R.id.cardBracos).setOnClickListener {
            selecionarGrupoMuscular("Braços")
        }

        findViewById<CardView>(R.id.cardAbdomen).setOnClickListener {
            selecionarGrupoMuscular("Abdômen")
        }

        findViewById<CardView>(R.id.cardOmbro).setOnClickListener {
            selecionarGrupoMuscular("Ombro")
        }
    }

    private fun selecionarGrupoMuscular(grupo: String) {
        val intent = Intent(this, SelecionarExercicios::class.java)
        intent.putExtra("fichaId", fichaId)
        intent.putExtra("diaSemana", diaSemana)
        intent.putExtra("grupoMuscular", grupo)
        startActivity(intent)
    }
}