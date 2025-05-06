package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fitguide.databinding.ActivityHomeAlunoBinding
import java.util.Calendar
import android.widget.TextView


class HomeAluno : AppCompatActivity() {
    private lateinit var binding: ActivityHomeAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        atualizarStatusTreinos()

        configurarBotoes()
    }

    private fun configurarBotoes() {
        binding.textViewDeslogar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

        binding.buttonMeuTreino.setOnClickListener {
            val intent = Intent(this, MeuTreino::class.java)
            startActivityForResult(intent, 1001)
        }


        binding.buttonMeuPerfil.setOnClickListener {
            val intent = Intent(this, MeuPerfil::class.java)
            startActivity(intent)
        }

        binding.buttonChat.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }
    }

    private fun atualizarStatusTreinos() {
        if (TreinoConcluidoStorage.isConcluido(Calendar.MONDAY)) {
            setDiaConcluido(binding.textViewSegunda, binding.textViewSegundaTreino)
        }
        if (TreinoConcluidoStorage.isConcluido(Calendar.TUESDAY)) {
            setDiaConcluido(binding.textViewTerca, binding.textViewTercaTreino)
        }
        if (TreinoConcluidoStorage.isConcluido(Calendar.WEDNESDAY)) {
            setDiaConcluido(binding.textViewQuarta, binding.textViewQuartaTreino)
        }
        if (TreinoConcluidoStorage.isConcluido(Calendar.THURSDAY)) {
            setDiaConcluido(binding.textViewQuinta, binding.textViewQuintaTreino)
        }
        if (TreinoConcluidoStorage.isConcluido(Calendar.FRIDAY)) {
            setDiaConcluido(binding.textViewSexta, binding.textViewSextaTreino)
        }
    }

    private fun setDiaConcluido(diaTextView: TextView, treinoTextView: TextView) {
        val corVerde = ContextCompat.getColor(this, android.R.color.holo_green_dark)
        diaTextView.setBackgroundColor(corVerde)
        treinoTextView.setBackgroundColor(corVerde)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            val diaFinalizado = data?.getIntExtra("diaFinalizado", -1)
            if (diaFinalizado != null && diaFinalizado != -1) {
                TreinoConcluidoStorage.marcarComoConcluido(diaFinalizado)
                when (diaFinalizado) {
                    Calendar.MONDAY -> setDiaConcluido(binding.textViewSegunda, binding.textViewSegundaTreino)
                    Calendar.TUESDAY -> setDiaConcluido(binding.textViewTerca, binding.textViewTercaTreino)
                    Calendar.WEDNESDAY -> setDiaConcluido(binding.textViewQuarta, binding.textViewQuartaTreino)
                    Calendar.THURSDAY -> setDiaConcluido(binding.textViewQuinta, binding.textViewQuintaTreino)
                    Calendar.FRIDAY -> setDiaConcluido(binding.textViewSexta, binding.textViewSextaTreino)
                }
            }
        }
    }


}
