package com.example.fitguide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MensagemAdapter(private val listaMensagens: List<Mensagem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TIPO_USUARIO = 0
        private const val TIPO_BOT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (listaMensagens[position].tipo == TipoMensagem.USUARIO) {
            TIPO_USUARIO
        } else {
            TIPO_BOT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TIPO_USUARIO) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_usuario, parent, false)
            UsuarioViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_bot, parent, false)
            BotViewHolder(view)
        }
    }

    override fun getItemCount(): Int = listaMensagens.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mensagem = listaMensagens[position]

        when (holder) {
            is UsuarioViewHolder -> holder.bind(mensagem)
            is BotViewHolder -> holder.bind(mensagem)
        }
    }

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMensagem: TextView = itemView.findViewById(R.id.textViewMensagemUsuario)

        fun bind(mensagem: Mensagem) {
            textViewMensagem.text = mensagem.texto
        }
    }

    class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMensagem: TextView = itemView.findViewById(R.id.textViewMensagemBot)

        fun bind(mensagem: Mensagem) {
            textViewMensagem.text = mensagem.texto
        }
    }
}
