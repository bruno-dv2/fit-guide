package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
            // Limpar a pilha de atividades para evitar retorno com botão voltar
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Configurar o botão Meu Treino
        binding.buttonMeuTreino.setOnClickListener {
            // Implementar navegação para a tela MeuTreino
            val intent = Intent(this, MeuTreino::class.java)
            startActivity(intent)
        }

        // Configurar o botão Meu Perfil
        binding.buttonMeuPerfil.setOnClickListener {
            // Implementar quando criar a tela MeuPerfil
            Toast.makeText(this, "Funcionalidade em desenvolvimento", Toast.LENGTH_SHORT).show()
        }

        // Configurar o botão de Chat para Dúvidas
        binding.buttonChat.setOnClickListener {
            Toast.makeText(this, "Chat em desenvolvimento", Toast.LENGTH_SHORT).show()
            // Quando implementar a tela Chat, descomente a linha abaixo
            // val intent = Intent(this, ChatActivity::class.java)
            // startActivity(intent)
        }
    }
}