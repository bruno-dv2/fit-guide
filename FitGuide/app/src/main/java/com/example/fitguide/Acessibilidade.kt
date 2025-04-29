package com.example.fitguide

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Acessibilidade : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acessibilidade)



        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val botaoSalvar = findViewById<Button>(R.id.botaoSalvar)

        val switchAltoContraste = findViewById<Switch>(R.id.switchAltoContraste)
        val switchTamanhoTexto = findViewById<Switch>(R.id.switchTamanhoTexto)
        val switchDescricaoImagens = findViewById<Switch>(R.id.switchDescricaoImagens)

        val sharedPreferences = getSharedPreferences("AcessibilidadePrefs", Context.MODE_PRIVATE)

        // Carregar configurações salvas
        switchAltoContraste.isChecked = sharedPreferences.getBoolean("altoContraste", false)
        switchTamanhoTexto.isChecked = sharedPreferences.getBoolean("tamanhoTexto", false)
        switchDescricaoImagens.isChecked = sharedPreferences.getBoolean("descricaoImagens", false)

        btnBack.setOnClickListener {
            finish()
        }

        botaoSalvar.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("altoContraste", switchAltoContraste.isChecked)
            editor.putBoolean("tamanhoTexto", switchTamanhoTexto.isChecked)
            editor.putBoolean("descricaoImagens", switchDescricaoImagens.isChecked)
            editor.apply()

            Toast.makeText(this, "Configurações salvas!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
