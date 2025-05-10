package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityCriarContaBinding

class CriarConta : AppCompatActivity() {

    private lateinit var binding: ActivityCriarContaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nomeEditText: EditText = binding.editTextNomeCompleto
        val emailEditText: EditText = binding.editTextEmail
        val pesoEditText: EditText = binding.editTextPeso
        val alturaEditText: EditText = binding.editTextAltura
        val senhaEditText: EditText = binding.editTextSenha
        val confirmarSenhaEditText: EditText = binding.editTextConfirmarSenha
        val codigoProfessorEditText: EditText = binding.editTextCodigoProfessor
        val criarContaButton: Button = binding.buttonCriarConta
        val botaoVoltar: ImageButton = binding.imageButtonBack // Get the ImageButton

        criarContaButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val email = emailEditText.text.toString()
            val peso = pesoEditText.text.toString()
            val altura = alturaEditText.text.toString()
            val senha = senhaEditText.text.toString()
            val confirmarSenha = confirmarSenhaEditText.text.toString()
            val codigoProfessor = codigoProfessorEditText.text.toString()

            // validacao basica de nome vazio
            if (nome.isEmpty() || email.isEmpty() || peso.isEmpty() || altura.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmarSenha) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // mensagem mockada de sucesso
            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }

        botaoVoltar.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }

}