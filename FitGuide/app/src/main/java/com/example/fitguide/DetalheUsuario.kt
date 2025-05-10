package com.example.fitguide

import android.content.Intent
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

        binding.textViewNomeUsuario.text = usuario.nome

        binding.editTextNomeCompleto.setText(usuario.nome)
        binding.editTextEmail.setText(usuario.email)
        binding.editTextPeso.setText(usuario.peso)
        binding.editTextAltura.setText(usuario.altura)
        binding.buttonRemoverUsuario.setOnClickListener {
            BancoTemporario.deleteUsuario(usuario)
            finish()
        }

        binding.buttonAdicionarFicha.setOnClickListener {
            val intent = Intent(this, AtribuirFicha::class.java)
            startActivity(intent)
            finish()
        }
    }
}