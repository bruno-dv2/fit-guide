package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MeuPerfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meu_perfil)

        val btnBack = findViewById<ImageView>(R.id.imageButtonBack)
        val botaoEditarPerfil = findViewById<Button>(R.id.buttonEditarPerfil)
        val botaoAcessibilidade = findViewById<Button>(R.id.buttonAcessibilidade)
        val botaoMinhasFichas = findViewById<Button>(R.id.buttonMinhasFichas)
        val excluirConta = findViewById<TextView>(R.id.textViewExcluirConta)

        btnBack.setOnClickListener {
            finish()
        }

        botaoEditarPerfil.setOnClickListener {
            startActivity(Intent(this, EditarPerfil::class.java))
        }

        botaoAcessibilidade.setOnClickListener {
            startActivity(Intent(this, Acessibilidade::class.java))
        }

        botaoMinhasFichas.setOnClickListener {
            // Aqui você pode abrir uma Activity de Minhas Fichas futuramente
        }

        excluirConta.setOnClickListener {
            // Aqui você pode colocar uma confirmação para excluir a conta futuramente
        }

    }
}
