package com.example.fitguide

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitguide.api.ChatGptClient
import com.example.fitguide.api.ChatGptRequest
import com.example.fitguide.api.Message
import kotlinx.coroutines.launch

class ChatbotActivity : AppCompatActivity() {

    private lateinit var editTextMensagem: EditText
    private lateinit var buttonEnviar: ImageButton
    private lateinit var recyclerViewMensagens: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val listaMensagens = mutableListOf<Mensagem>()
    private lateinit var adapter: MensagemAdapter

    // Lista para armazenar o histórico de mensagens para a API do ChatGPT
    private val chatHistory = mutableListOf<Message>()
    
    // Número máximo de mensagens no histórico (excluindo a mensagem do sistema)
    private val MAX_HISTORY_SIZE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        editTextMensagem = findViewById(R.id.editTextMensagem)
        buttonEnviar = findViewById(R.id.buttonEnviar)
        recyclerViewMensagens = findViewById(R.id.recyclerViewMensagens)
        progressBar = findViewById(R.id.progressBar)

        adapter = MensagemAdapter(listaMensagens)
        recyclerViewMensagens.layoutManager = LinearLayoutManager(this)
        recyclerViewMensagens.adapter = adapter

        // Adicionar uma mensagem inicial do sistema para contextualizar o ChatGPT
        chatHistory.add(Message(
            role = "system",
            content = "Você é um assistente de fitness chamado FitGuide. Você ajuda com dúvidas sobre exercícios, nutrição, treinos e saúde em geral. Mantenha suas respostas concisas, informativas e motivadoras. Você deve responder em português do Brasil."
        ))

        buttonEnviar.setOnClickListener {
            val mensagemTexto = editTextMensagem.text.toString().trim()

            if (mensagemTexto.isNotEmpty()) {
                // Adicionar mensagem do usuário à interface
                listaMensagens.add(Mensagem(mensagemTexto, TipoMensagem.USUARIO))
                adapter.notifyItemInserted(listaMensagens.size - 1)
                recyclerViewMensagens.scrollToPosition(listaMensagens.size - 1)
                editTextMensagem.text.clear()

                // Adicionar mensagem do usuário ao histórico
                chatHistory.add(Message(role = "user", content = mensagemTexto))
                
                // Limitar o histórico de mensagens (excluindo a mensagem do sistema)
                limitarHistorico()

                // Mostrar indicador de carregamento
                progressBar.visibility = View.VISIBLE
                buttonEnviar.isEnabled = false

                // Chamar a API do ChatGPT
                obterRespostaChatGpt()
            }
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
    
    private fun limitarHistorico() {
        // Mantém a mensagem do sistema (índice 0) e as últimas MAX_HISTORY_SIZE mensagens
        if (chatHistory.size > MAX_HISTORY_SIZE + 1) {
            // Número de mensagens a remover
            val excessMessages = chatHistory.size - (MAX_HISTORY_SIZE + 1)
            
            // Remove as mensagens mais antigas, preservando a mensagem do sistema
            for (i in 1..excessMessages) {
                chatHistory.removeAt(1) // Remove sempre a segunda mensagem (após a do sistema)
            }
        }
    }

    private fun obterRespostaChatGpt() {
        lifecycleScope.launch {
            try {
                val request = ChatGptRequest(
                    messages = chatHistory
                )

                val response = ChatGptClient.service.getChatCompletion(
                    authorization = ChatGptClient.getAuthorizationHeader(),
                    request = request
                )

                if (response.isSuccessful && response.body() != null) {
                    val chatGptResponse = response.body()!!
                    val mensagemResposta = chatGptResponse.choices.firstOrNull()?.message

                    if (mensagemResposta != null) {
                        // Adicionar resposta ao histórico
                        chatHistory.add(mensagemResposta)
                        
                        // Limitar o histórico novamente após adicionar a resposta
                        limitarHistorico()

                        // Adicionar resposta à interface
                        val respostaBot = mensagemResposta.content
                        listaMensagens.add(Mensagem(respostaBot, TipoMensagem.BOT))
                        adapter.notifyItemInserted(listaMensagens.size - 1)
                        recyclerViewMensagens.scrollToPosition(listaMensagens.size - 1)
                    } else {
                        exibirErro("Não foi possível obter uma resposta do assistente.")
                    }
                } else {
                    exibirErro("Erro na comunicação com o assistente: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                exibirErro("Erro ao conectar com o assistente: ${e.message}")
            } finally {
                // Esconder indicador de carregamento
                progressBar.visibility = View.GONE
                buttonEnviar.isEnabled = true
            }
        }
    }

    private fun exibirErro(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()

        // Adicionar uma mensagem de erro na interface
        listaMensagens.add(Mensagem("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente.", TipoMensagem.BOT))
        adapter.notifyItemInserted(listaMensagens.size - 1)
        recyclerViewMensagens.scrollToPosition(listaMensagens.size - 1)
    }
}
