package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get references to the views
        val emailEditText: EditText = binding.editTextEmail
        val passwordEditText: EditText = binding.editTextSenha
        val alunoCheckBox: CheckBox = binding.checkBoxAluno
        val professorCheckBox: CheckBox = binding.checkBoxProfessor
        val entrarButton: Button = binding.buttonEntrar
        val criarContaButton: Button = binding.buttonCriarConta
        val esqueceuSenhaTextView: TextView = binding.textViewEsqueceuSenha

        // Set click listeners
        entrarButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val isAluno = alunoCheckBox.isChecked
            val isProfessor = professorCheckBox.isChecked

            // Basic validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isAluno && !isProfessor) {
                Toast.makeText(this, "Selecione o tipo de acesso", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Aqui seria logica com banco de dados, para funcionar, so coloquei um bem-sucedido aqui
            Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
            // You would then navigate to the next activity
            if (isProfessor) {
                val intent = Intent(this, HomeProfessor::class.java)
                startActivity(intent)
                finish()
            } else if (isAluno) {
                val intent = Intent(this, HomeAluno::class.java)
                startActivity(intent)
                finish()
            }
        }

        criarContaButton.setOnClickListener {
            // Navigate to the create account activity
            val intent = Intent(this, CriarConta::class.java)
            startActivity(intent)
        }

        esqueceuSenhaTextView.setOnClickListener {
            // navegar para esqueceu a senha
            Toast.makeText(this, "Esqueceu a senha", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, EsqueceuSenha::class.java)
             startActivity(intent)
        }

        // Checkbox logic
        alunoCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                professorCheckBox.isChecked = false
            }
        }

        professorCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alunoCheckBox.isChecked = false
            }
        }
    }
}