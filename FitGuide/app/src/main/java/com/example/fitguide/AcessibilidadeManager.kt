package com.example.fitguide

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

object AcessibilidadeManager {

    fun aplicarAcessibilidade(context: Context, rootView: View) {
        val sharedPreferences = context.getSharedPreferences("AcessibilidadePrefs", Context.MODE_PRIVATE)
        val altoContraste = sharedPreferences.getBoolean("altoContraste", false)
        val tamanhoTexto = sharedPreferences.getBoolean("tamanhoTexto", false)

        if (altoContraste) {
            rootView.setBackgroundColor(Color.BLACK)
            aplicarAltoContraste(rootView)
        } else {
            rootView.setBackgroundColor(Color.WHITE)
            aplicarNormal(rootView)
        }

        if (tamanhoTexto) {
            aumentarTamanhoTexto(rootView)
        }
    }

    private fun aplicarAltoContraste(view: View) {
        when (view) {
            is TextView -> view.setTextColor(Color.WHITE)
            is Switch -> view.setTextColor(Color.WHITE)
            is Button -> {
                view.setBackgroundColor(Color.BLACK)
                view.setTextColor(Color.WHITE)
                adicionarBordaBranca(view)
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                aplicarAltoContraste(view.getChildAt(i))
            }
        }
    }

    private fun aplicarNormal(view: View) {
        when (view) {
            is TextView -> view.setTextColor(Color.BLACK)
            is Switch -> view.setTextColor(Color.BLACK)
            is Button -> {
                view.setBackgroundColor(Color.LTGRAY)
                view.setTextColor(Color.BLACK)
                removerBorda(view)
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                aplicarNormal(view.getChildAt(i))
            }
        }
    }

    private fun aumentarTamanhoTexto(view: View) {
        if (view is TextView) {
            view.textSize = view.textSize / view.resources.displayMetrics.scaledDensity + 4
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                aumentarTamanhoTexto(view.getChildAt(i))
            }
        }
    }

    private fun adicionarBordaBranca(button: Button) {
        val border = GradientDrawable()
        border.setColor(Color.BLACK) // Cor de fundo
        border.setStroke(3, Color.WHITE) // Borda branca
        button.background = border
    }

    private fun removerBorda(button: Button) {
        val border = GradientDrawable()
        border.setColor(Color.LTGRAY) // Volta fundo cinza normal
        border.setStroke(0, Color.TRANSPARENT) // Sem borda
        button.background = border
    }
}
