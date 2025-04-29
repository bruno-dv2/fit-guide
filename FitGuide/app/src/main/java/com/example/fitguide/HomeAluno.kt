package com.example.fitguide

import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD
=======
import android.widget.Toast
>>>>>>> 47db03ea02c08ce83237e76ba8b59508e6942a8e
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
<<<<<<< HEAD
=======
            // Limpar a pilha de atividades para evitar retorno com botão voltar
>>>>>>> 47db03ea02c08ce83237e76ba8b59508e6942a8e
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Configurar o botão Meu Treino
        binding.buttonMeuTreino.setOnClickListener {
<<<<<<< HEAD
=======
            // Implementar navegação para a tela MeuTreino
>>>>>>> 47db03ea02c08ce83237e76ba8b59508e6942a8e
            val intent = Intent(this, MeuTreino::class.java)
            startActivity(intent)
        }

        // Configurar o botão Meu Perfil
        binding.buttonMeuPerfil.setOnClickListener {
<<<<<<< HEAD
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
=======
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
>>>>>>> 47db03ea02c08ce83237e76ba8b59508e6942a8e
