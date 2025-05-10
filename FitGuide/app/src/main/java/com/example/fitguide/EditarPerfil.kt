package com.example.fitguide

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarPerfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)


        val btnVoltar = findViewById<ImageView>(R.id.btnBack)
        val btnSalvar = findViewById<Button>(R.id.botaoSalvar)

        val campoNome = findViewById<EditText>(R.id.campoNome)
        val campoEmail = findViewById<EditText>(R.id.campoEmail)
        val campoPeso = findViewById<EditText>(R.id.campoPeso)
        val campoAltura = findViewById<EditText>(R.id.campoAltura)
        val campoSenha = findViewById<EditText>(R.id.campoSenha)
        val campoConfirmarSenha = findViewById<EditText>(R.id.campoConfirmarSenha)

        // Botão voltar
        btnVoltar.setOnClickListener {
            finish()
        }

        // Botão salvar
        btnSalvar.setOnClickListener {
            val nome = campoNome.text.toString()
            val email = campoEmail.text.toString()
            val peso = campoPeso.text.toString()
            val altura = campoAltura.text.toString()
            val senha = campoSenha.text.toString()
            val confirmarSenha = campoConfirmarSenha.text.toString()

            // Validação
            if (nome.isEmpty() || email.isEmpty() || peso.isEmpty() || altura.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmarSenha) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}
