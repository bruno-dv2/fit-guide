package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityGerenciarUsuariosBinding

class GerenciarUsuarios : AppCompatActivity() {

    private lateinit var binding: ActivityGerenciarUsuariosBinding
    private lateinit var usuariosContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerenciarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuariosContainer = binding.usuariosContainer

        // Configurar o botão de voltar
        binding.imageButtonBack.setOnClickListener {
            val intent = Intent(this, HomeProfessor::class.java)
            startActivity(intent)
            finish()
        }

        // Adicionar usuários de exemplo se não existirem
        if (BancoTemporario.listaUsuarios.isEmpty()) {
            adicionarUsuariosExemplo()
        }

        atualizarListaUsuarios()
    }

    private fun adicionarUsuariosExemplo() {
        BancoTemporario.listaUsuarios.add(Usuario("João Silva", "joao@email.com", "75", "175"))
        BancoTemporario.listaUsuarios.add(Usuario("Maria Oliveira", "maria@email.com", "65", "165"))
        BancoTemporario.listaUsuarios.add(Usuario("Pedro Santos", "pedro@email.com", "80", "180"))
        BancoTemporario.listaUsuarios.add(Usuario("Ana Costa", "ana@email.com", "60", "160"))
    }

    override fun onResume() {
        super.onResume()
        atualizarListaUsuarios()
    }

    private fun atualizarListaUsuarios() {
        // Limpar a lista atual
        usuariosContainer.removeAllViews()

        if (BancoTemporario.listaUsuarios.isEmpty()) {
            val mensagemVazia = TextView(this)
            mensagemVazia.text = "Nenhum aluno encontrado"
            mensagemVazia.textSize = 16f
            mensagemVazia.setPadding(16, 16, 16, 16)
            usuariosContainer.addView(mensagemVazia)
        } else {
            // Adicionar cada usuário à lista
            for (usuario in BancoTemporario.listaUsuarios) {
                val usuarioView = layoutInflater.inflate(
                    R.layout.item_usuario,
                    usuariosContainer,
                    false
                )

                val nomeTextView = usuarioView.findViewById<TextView>(R.id.textViewNomeUsuario)
                val emailTextView = usuarioView.findViewById<TextView>(R.id.textViewEmailUsuario)

                nomeTextView.text = usuario.nome
                emailTextView.text = usuario.email

                usuarioView.setOnClickListener {
                    val intent = Intent(this, DetalheUsuario::class.java)
                    intent.putExtra("usuarioId", usuario.id)
                    startActivity(intent)
                }

                usuariosContainer.addView(usuarioView)
            }
        }
    }
}