package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityEsqueceuSenhaBinding

class EsqueceuSenha : AppCompatActivity() {

    private lateinit var binding: ActivityEsqueceuSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get references to the views
        val emailEditText: EditText = binding.editTextEmail
        val novaSenhaEditText: EditText = binding.editTextNovaSenha
        val confirmarSenhaEditText: EditText = binding.editTextConfirmarSenha
        val confirmarButton: Button = binding.buttonConfirmar
        val botaoVoltar: ImageButton = binding.imageButtonBack

        // Set click listener for the "Confirmar" button
        confirmarButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val novaSenha = novaSenhaEditText.text.toString()
            val confirmarSenha = confirmarSenhaEditText.text.toString()

            // Basic validation
            if (email.isEmpty() || novaSenha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (novaSenha != confirmarSenha) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Here you would typically send a password reset request or update the password in a database
            // For now, we'll just show a success message
            Toast.makeText(this, "Um email para validação foi enviado", Toast.LENGTH_SHORT).show()
            // You can add code here to navigate to the login screen if needed
        }

        // Set click listener for the back button
        botaoVoltar.setOnClickListener {
            // Navigate back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }
}