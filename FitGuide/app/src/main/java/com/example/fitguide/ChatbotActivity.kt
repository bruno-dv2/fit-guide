package com.example.fitguide

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatbotActivity : AppCompatActivity() {

    private lateinit var editTextMensagem: EditText
    private lateinit var buttonEnviar: ImageButton
    private lateinit var recyclerViewMensagens: RecyclerView
    private val listaMensagens = mutableListOf<Mensagem>()
    private lateinit var adapter: MensagemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        editTextMensagem = findViewById(R.id.editTextMensagem)
        buttonEnviar = findViewById(R.id.buttonEnviar)
        recyclerViewMensagens = findViewById(R.id.recyclerViewMensagens)

        adapter = MensagemAdapter(listaMensagens)
        recyclerViewMensagens.layoutManager = LinearLayoutManager(this)
        recyclerViewMensagens.adapter = adapter

        buttonEnviar.setOnClickListener {
            val mensagemTexto = editTextMensagem.text.toString().trim()

            if (mensagemTexto.isNotEmpty()) {
                listaMensagens.add(Mensagem(mensagemTexto, TipoMensagem.USUARIO))
                adapter.notifyItemInserted(listaMensagens.size - 1)
                recyclerViewMensagens.scrollToPosition(listaMensagens.size - 1)
                editTextMensagem.text.clear()

                simularRespostaBot()
            }
        }

        // ✅ Correção aqui! btnBack é ImageView
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun simularRespostaBot() {
        val respostaBot = "Recebi sua pergunta! Em breve responderei."
        listaMensagens.add(Mensagem(respostaBot, TipoMensagem.BOT))
        adapter.notifyItemInserted(listaMensagens.size - 1)
        recyclerViewMensagens.scrollToPosition(listaMensagens.size - 1)
    }
}
