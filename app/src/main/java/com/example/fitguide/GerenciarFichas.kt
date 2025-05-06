package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class GerenciarFichas : AppCompatActivity() {

    private lateinit var fichasContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_fichas)

        fichasContainer = findViewById(R.id.fichasContainer)

        // Configurar o botão de voltar
        val botaoVoltar = findViewById<ImageButton>(R.id.imageButtonBack)
        botaoVoltar.setOnClickListener {
            finish()
        }

        // Configurar o botão de criar ficha
        findViewById<CardView>(R.id.cardCriarFicha).setOnClickListener {
            val intent = Intent(this, CriarEditarFicha::class.java)
            startActivity(intent)
        }

        // Adicionar fichas de exemplo se não existirem
        if (BancoTemporario.listaFichas.isEmpty()) {
            adicionarFichasExemplo()
        }

        atualizarListaFichas()
    }

    private fun adicionarFichasExemplo() {
        // Ficha 1: Treino Iniciante
        val fichaIniciante = Ficha("Treino Iniciante")

        // Adicionar dia da semana e exercícios
        val segundaIniciante = DiaTreino("SEGUNDA-FEIRA")
        segundaIniciante.exercicios.add(
            ExercicioTreino(
                Exercicio("Supino Reto", "Barra e Banco", "Deite-se no banco, segure a barra e faça o movimento", "Peitoral, Tríceps, Ombros", "", "Peito"),
                3, 12
            )
        )

        val quartaIniciante = DiaTreino("QUARTA-FEIRA")
        quartaIniciante.exercicios.add(
            ExercicioTreino(
                Exercicio("Agachamento", "Barra", "Coloque a barra nos ombros e agache", "Quadríceps, Glúteos, Isquiotibiais", "", "Pernas"),
                3, 12
            )
        )

        val sextaIniciante = DiaTreino("SEXTA-FEIRA")
        sextaIniciante.exercicios.add(
            ExercicioTreino(
                Exercicio("Puxada Frontal", "Máquina", "Puxe a barra para baixo em direção ao peito", "Dorsais, Bíceps", "", "Costas"),
                3, 12
            )
        )

        fichaIniciante.diasTreino["SEGUNDA-FEIRA"] = segundaIniciante
        fichaIniciante.diasTreino["QUARTA-FEIRA"] = quartaIniciante
        fichaIniciante.diasTreino["SEXTA-FEIRA"] = sextaIniciante

        // Ficha 2: Treino Intermediário
        val fichaIntermediario = Ficha("Treino Intermediário")
        fichaIntermediario.diasTreino["SEGUNDA-FEIRA"] = DiaTreino("SEGUNDA-FEIRA")
        fichaIntermediario.diasTreino["TERÇA-FEIRA"] = DiaTreino("TERÇA-FEIRA")
        fichaIntermediario.diasTreino["QUINTA-FEIRA"] = DiaTreino("QUINTA-FEIRA")
        fichaIntermediario.diasTreino["SEXTA-FEIRA"] = DiaTreino("SEXTA-FEIRA")

        BancoTemporario.listaFichas.add(fichaIniciante)
        BancoTemporario.listaFichas.add(fichaIntermediario)
    }

    override fun onResume() {
        super.onResume()
        atualizarListaFichas()
    }

    private fun atualizarListaFichas() {
        fichasContainer.removeAllViews()

        for (ficha in BancoTemporario.listaFichas) {
            val fichaView = LayoutInflater.from(this).inflate(R.layout.item_ficha, fichasContainer, false)

            val tituloFicha = fichaView.findViewById<TextView>(R.id.textViewTituloFicha)
            val diasSemana = fichaView.findViewById<TextView>(R.id.textViewDiasSemana)
            val editarFicha = fichaView.findViewById<TextView>(R.id.textViewEditar)

            tituloFicha.text = ficha.titulo
            diasSemana.text = ficha.diasTreino.keys.joinToString(", ")

            editarFicha.setOnClickListener {
                val intent = Intent(this, CriarEditarFicha::class.java)
                intent.putExtra("fichaId", ficha.id)
                startActivity(intent)
            }

            fichasContainer.addView(fichaView)
        }
    }
}