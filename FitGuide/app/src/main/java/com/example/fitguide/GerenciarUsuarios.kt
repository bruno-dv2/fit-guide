package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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
        val botaoVoltar: ImageButton = binding.imageButtonBack
        botaoVoltar.setOnClickListener {
            val intent = Intent(this, HomeProfessor::class.java)
            startActivity(intent)
            finish()
        }

        // Adicionar usuários de exemplo
        if (BancoTemporario.listaUsuarios.isEmpty()) {
            adicionarUsuariosExemplo()
        }

        updateUI()
    }

    private fun adicionarUsuariosExemplo() {
        BancoTemporario.listaUsuarios.add(Usuario("Cafú", "cafu@email.com", "80", "178"))
        BancoTemporario.listaUsuarios.add(Usuario("Rivaldo", "rivaldo@email.com", "75", "186"))
        BancoTemporario.listaUsuarios.add(Usuario("Ronaldinho Gaúcho", "ronaldinho@email.com", "82", "180"))
        BancoTemporario.listaUsuarios.add(Usuario("Kaká", "kaka@email.com", "78", "183"))
        BancoTemporario.listaUsuarios.add(Usuario("Roberto Carlos", "roberto@email.com", "68", "168"))
        BancoTemporario.listaUsuarios.add(Usuario("Dida", "dida@email.com", "90", "195"))
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        usuariosContainer.removeAllViews()

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 16, 0, 0)
        }

        for (usuario in BancoTemporario.listaUsuarios) {
            val usuarioView = TextView(this).apply {
                val idFormatado = String.format("%03d", usuario.id)
                text = "${usuario.nome} - ${idFormatado}"
                textSize = 20f
                setPadding(32, 48, 32, 48)
                setTextColor(resources.getColor(android.R.color.black))
                setBackgroundResource(R.drawable.user_card_background)
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                this.layoutParams = layoutParams

                setOnClickListener {
                    val intent = Intent(this@GerenciarUsuarios, DetalheUsuario::class.java)
                    intent.putExtra("usuarioId", usuario.id)
                    startActivity(intent)
                }
            }

            usuariosContainer.addView(usuarioView)
        }
    }
}