package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class CriarEditarFicha : AppCompatActivity() {

    private var fichaId: Int = -1
    private lateinit var ficha: Ficha
    private val diasSemana = listOf("SEGUNDA-FEIRA", "TERÇA-FEIRA", "QUARTA-FEIRA", "QUINTA-FEIRA", "SEXTA-FEIRA")
    private val botoesSelecao = mutableListOf<CardView>()
    private val diasSelecionados = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_editar_ficha)

        // Configurar o botão de voltar
        val botaoVoltar = findViewById<ImageButton>(R.id.imageButtonBack)
        botaoVoltar.setOnClickListener {
            finish()
        }

        // Obter referências para os cards dos dias da semana
        botoesSelecao.add(findViewById(R.id.cardSegunda))
        botoesSelecao.add(findViewById(R.id.cardTerca))
        botoesSelecao.add(findViewById(R.id.cardQuarta))
        botoesSelecao.add(findViewById(R.id.cardQuinta))
        botoesSelecao.add(findViewById(R.id.cardSexta))

        // Configurar listeners para os cards
        for (i in botoesSelecao.indices) {
            val card = botoesSelecao[i]
            val dia = diasSemana[i]

            card.setOnClickListener {
                if (diasSelecionados.contains(dia)) {
                    // Desmarcar dia
                    diasSelecionados.remove(dia)
                    card.setCardBackgroundColor(resources.getColor(android.R.color.darker_gray))
                } else {
                    // Marcar dia
                    diasSelecionados.add(dia)
                    card.setCardBackgroundColor(resources.getColor(android.R.color.holo_purple))
                }
            }
        }

        // Verificar se estamos editando uma ficha existente ou criando uma nova
        fichaId = intent.getIntExtra("fichaId", -1)

        if (fichaId != -1) {
            // Estamos editando uma ficha existente
            ficha = BancoTemporario.listaFichas.find { it.id == fichaId }
                ?: run {
                    finish()
                    return
                }

            // Preencher os campos com os dados da ficha
            findViewById<EditText>(R.id.editTextTituloFicha).setText(ficha.titulo)

            // Marcar os dias da semana selecionados
            for (i in diasSemana.indices) {
                val dia = diasSemana[i]
                if (ficha.diasTreino.containsKey(dia)) {
                    diasSelecionados.add(dia)
                    botoesSelecao[i].setCardBackgroundColor(resources.getColor(android.R.color.holo_purple))
                }
            }
        } else {
            // Estamos criando uma nova ficha
            ficha = Ficha("")
        }

        // Configurar o botão próximo
        findViewById<Button>(R.id.buttonProximo).setOnClickListener {
            if (validarDados()) {
                salvarDadosBasicos()
                if (diasSelecionados.isNotEmpty()) {
                    // Escolher o primeiro dia selecionado para configurar
                    val diaSelecionado = diasSelecionados.first()
                    abrirSelecaoGrupoMuscular(diaSelecionado)
                } else {
                    Toast.makeText(this, "Selecione pelo menos um dia da semana", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validarDados(): Boolean {
        val titulo = findViewById<EditText>(R.id.editTextTituloFicha).text.toString()
        if (titulo.isBlank()) {
            Toast.makeText(this, "Informe um título para a ficha", Toast.LENGTH_SHORT).show()
            return false
        }

        if (diasSelecionados.isEmpty()) {
            Toast.makeText(this, "Selecione pelo menos um dia da semana", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun salvarDadosBasicos() {
        ficha.titulo = findViewById<EditText>(R.id.editTextTituloFicha).text.toString()

        // Remover dias que não estão mais selecionados
        val diasParaRemover = ficha.diasTreino.keys.filter { !diasSelecionados.contains(it) }
        for (dia in diasParaRemover) {
            ficha.diasTreino.remove(dia)
        }

        // Adicionar novos dias selecionados
        for (dia in diasSelecionados) {
            if (!ficha.diasTreino.containsKey(dia)) {
                ficha.diasTreino[dia] = DiaTreino(dia)
            }
        }

        // Adicionar à lista se for uma nova ficha
        if (fichaId == -1) {
            BancoTemporario.listaFichas.add(ficha)
        }
    }

    private fun abrirSelecaoGrupoMuscular(diaSemana: String) {
        val intent = Intent(this, SelecionarGrupoMuscular::class.java)
        intent.putExtra("fichaId", ficha.id)
        intent.putExtra("diaSemana", diaSemana)
        startActivity(intent)
    }
}