package com.example.fitguide

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityDetalheUsuarioBinding

class DetalheUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityDetalheUsuarioBinding
    private var usuarioId: Int = -1
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioId = intent.getIntExtra("usuarioId", -1)
        if (usuarioId == -1) {
            finish()
            return
        }

        usuario = BancoTemporario.listaUsuarios.find { it.id == usuarioId }
            ?: run {
                finish()
                return
            }

        // Configurar o título com o nome do usuário
        binding.textViewNomeUsuario.text = usuario.nome

        // Preencher os campos com os dados do usuário
        binding.editTextNomeCompleto.setText(usuario.nome)
        binding.editTextEmail.setText(usuario.email)
        binding.editTextPeso.setText(usuario.peso)
        binding.editTextAltura.setText(usuario.altura)

        // Configurar o botão de remover usuário
        binding.buttonRemoverUsuario.setOnClickListener {
            BancoTemporario.deleteUsuario(usuario)
            finish()
        }

        // Configurar o botão de adicionar ficha
        binding.buttonAdicionarFicha.setOnClickListener {
            // Aqui iria a lógica para adicionar ficha
            // Por enquanto, apenas fecha a tela
            finish()
        }
    }
}