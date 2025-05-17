package com.example.fitguide

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Buscar o usuário no banco temporário
        val usuarioEncontrado = BancoTemporario.listaUsuarios.find { it.id == usuarioId }
        if (usuarioEncontrado == null) {
            Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        usuario = usuarioEncontrado

        // Preencher a interface com os dados do usuário
        binding.textViewNomeUsuario.text = usuario.nome
        binding.editTextNomeCompleto.setText(usuario.nome)
        binding.editTextEmail.setText(usuario.email)
        binding.editTextPeso.setText(usuario.peso)
        binding.editTextAltura.setText(usuario.altura)

        // Configurar o botão de voltar
        binding.imageButtonBack.setOnClickListener {
            finish()
        }

        // Configurar o botão de salvar alterações
        binding.buttonSalvar.setOnClickListener {
            salvarAlteracoes()
        }

        // Configurar o botão de remover aluno
        binding.buttonRemoverUsuario.setOnClickListener {
            confirmarRemocao()
        }

        // Configurar o botão de adicionar ficha (será implementado na Fase 3)
        binding.buttonAdicionarFicha.setOnClickListener {
            Toast.makeText(this, "Funcionalidade disponível na próxima fase", Toast.LENGTH_SHORT).show()
        }
    }

    private fun salvarAlteracoes() {
        val nome = binding.editTextNomeCompleto.text.toString()
        val email = binding.editTextEmail.text.toString()
        val peso = binding.editTextPeso.text.toString()
        val altura = binding.editTextAltura.text.toString()

        // Validar os campos
        if (nome.isBlank() || email.isBlank()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        // Atualizar os dados do usuário
        usuario.nome = nome
        usuario.email = email
        usuario.peso = peso
        usuario.altura = altura

        // Atualizar o título com o novo nome
        binding.textViewNomeUsuario.text = nome

        Toast.makeText(this, "Alterações salvas com sucesso", Toast.LENGTH_SHORT).show()
    }

    private fun confirmarRemocao() {
        AlertDialog.Builder(this)
            .setTitle("Remover Aluno")
            .setMessage("Tem certeza que deseja remover este aluno?")
            .setPositiveButton("Sim") { _, _ ->
                BancoTemporario.deleteUsuario(usuario)
                Toast.makeText(this, "Aluno removido com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Não", null)
            .show()
    }
}