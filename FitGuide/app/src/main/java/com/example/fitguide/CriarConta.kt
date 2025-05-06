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

        // Remove the ActionBar back button
        // supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get references to the views
        val nomeEditText: EditText = binding.editTextNomeCompleto
        val emailEditText: EditText = binding.editTextEmail
        val pesoEditText: EditText = binding.editTextPeso
        val alturaEditText: EditText = binding.editTextAltura
        val senhaEditText: EditText = binding.editTextSenha
        val confirmarSenhaEditText: EditText = binding.editTextConfirmarSenha
        val codigoProfessorEditText: EditText = binding.editTextCodigoProfessor
        val criarContaButton: Button = binding.buttonCriarConta
        val botaoVoltar: ImageButton = binding.imageButtonBack // Get the ImageButton

        // Set click listener for the "Criar conta" button
        criarContaButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val email = emailEditText.text.toString()
            val peso = pesoEditText.text.toString()
            val altura = alturaEditText.text.toString()
            val senha = senhaEditText.text.toString()
            val confirmarSenha = confirmarSenhaEditText.text.toString()
            val codigoProfessor = codigoProfessorEditText.text.toString()

            // Basic validation
            if (nome.isEmpty() || email.isEmpty() || peso.isEmpty() || altura.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmarSenha) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Here you would typically save the user data to a database
            // For now, we'll just show a success message
            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Close the activity and go back to MainActivity
        }

        // Set click listener for the back button
        botaoVoltar.setOnClickListener {
            // Navigate back to HomeProfessor
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }

    // Remove the onOptionsItemSelected method since we're not using the ActionBar back button anymore
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    */
}