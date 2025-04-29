package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityHomeAlunoBinding

class HomeAluno : AppCompatActivity() {
    private lateinit var binding: ActivityHomeAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar o botão de deslogar
        binding.textViewDeslogar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Configurar o botão Meu Treino
        binding.buttonMeuTreino.setOnClickListener {
            val intent = Intent(this, MeuTreino::class.java)
            startActivity(intent)
        }

        // Configurar o botão Meu Perfil
        binding.buttonMeuPerfil.setOnClickListener {
            val intent = Intent(this, MeuPerfil::class.java)
            startActivity(intent)
        }

        // Configurar o botão de Chat para Dúvidas
        binding.botaoChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }
    }
}
