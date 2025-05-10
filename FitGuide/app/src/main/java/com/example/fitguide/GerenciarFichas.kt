package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitguide.databinding.ActivityGerenciarFichasBinding

class GerenciarFichas : AppCompatActivity() {

    private lateinit var binding: ActivityGerenciarFichasBinding
    private val fichasList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerenciarFichasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Voltar para a Home
        binding.imageButtonBack.setOnClickListener {
            val intent = Intent(this, HomeProfessor::class.java)
            startActivity(intent)
            finish()
        }

        // Criar nova ficha
        binding.buttonCriarFicha.setOnClickListener {
            val intent = Intent(this, CriarFicha::class.java)
            startActivityForResult(intent, 1)
        }

        // Adiciona fichas simuladas
        fichasList.addAll(listOf("Ficha A", "Ficha B", "Ficha C"))
        updateFichasDisplay()
    }

    private fun updateFichasDisplay() {
        binding.fichasContainer.removeAllViews()
        for (ficha in fichasList) {
            val fichaLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 0, 0, 16)
            }

            val nomeView = TextView(this).apply {
                text = ficha
                textSize = 16f
                setPadding(16, 16, 16, 8)
            }

            val editarDeletar = TextView(this).apply {
                text = "editar/deletar"
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                setPadding(16, 0, 16, 8)
                setOnClickListener {
                    val intent = Intent(this@GerenciarFichas, EditarFicha::class.java)
                    intent.putExtra("fichaParaEditar", ficha)
                    startActivityForResult(intent, 2)
                }
            }

            fichaLayout.addView(nomeView)
            fichaLayout.addView(editarDeletar)
            binding.fichasContainer.addView(fichaLayout)
        }
    }

    private fun addFicha(fichaName: String) {
        fichasList.add(fichaName)
        updateFichasDisplay()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    // Adding a new ficha
                    val newFicha = data?.getStringExtra("newFicha")
                    newFicha?.let { addFicha(it) }
                }
                2 -> {
                    // Deleting a ficha
                    val deletedFicha = data?.getStringExtra("deletedFicha")
                    deletedFicha?.let {
                        // Remove the ficha from the list
                        fichasList.remove(it)
                        updateFichasDisplay()  // Update the UI after removing
                    }
                }
            }
        }
    }
}
