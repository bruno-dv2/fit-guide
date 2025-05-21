package com.example.fitguide

import android.os.Bundle
import android.util.Log
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

    private val TAG = "ChatbotActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        // Inicializar o ChatGptClient com o contexto da aplicação
        ChatGptClient.initialize(applicationContext)

        editTextMensagem = findViewById(R.id.editTextMensagem)
        buttonEnviar = findViewById(R.id.buttonEnviar)
        recyclerViewMensagens = findViewById(R.id.recyclerViewMensagens)
        progressBar = findViewById(R.id.progressBar)

        // Configurar o RecyclerView com layout manager que mantém a posição do scroll
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true // Faz com que a lista comece de baixo para cima

        recyclerViewMensagens.layoutManager = layoutManager
        recyclerViewMensagens.itemAnimator = null // Remove animações que podem atrapalhar o scroll
        adapter = MensagemAdapter(listaMensagens)
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
                scrollToBottom() // Usar método personalizado para scroll
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
                Log.d(TAG, "Iniciando requisição para ChatGPT")
                val request = ChatGptRequest(
                    model = "gpt-4o",
                    messages = chatHistory,
                    temperature = 0.7
                )

                Log.d(TAG, "Enviando requisição: ${request.messages.size} mensagens")
                val response = ChatGptClient.service.getChatCompletion(
                    authorization = ChatGptClient.getAuthorizationHeader(),
                    request = request
                )

                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "Resposta recebida com sucesso: ${response.code()}")
                    val chatGptResponse = response.body()!!
                    val mensagemResposta = chatGptResponse.choices.firstOrNull()?.message

                    if (mensagemResposta != null) {
                        Log.d(TAG, "Conteúdo da resposta: ${mensagemResposta.content.take(50)}...")
                        // Adicionar resposta ao histórico
                        chatHistory.add(mensagemResposta)

                        // Limitar o histórico novamente após adicionar a resposta
                        limitarHistorico()

                        // Adicionar resposta à interface
                        val respostaBot = mensagemResposta.content
                        listaMensagens.add(Mensagem(respostaBot, TipoMensagem.BOT))
                        adapter.notifyItemInserted(listaMensagens.size - 1)
                        scrollToBottom() // Usar método personalizado para scroll
                    } else {
                        Log.e(TAG, "Mensagem de resposta nula")
                        exibirErro("Não foi possível obter uma resposta do assistente.")
                    }
                } else {
                    Log.e(TAG, "Erro na resposta: ${response.code()} - ${response.message()}")
                    Log.e(TAG, "Corpo do erro: ${response.errorBody()?.string()}")
                    exibirErro("Erro na comunicação com o assistente: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exceção ao chamar API: ${e.message}", e)
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
        Log.e(TAG, "Erro exibido: $mensagem")

        // Adicionar uma mensagem de erro na interface
        listaMensagens.add(Mensagem("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente.", TipoMensagem.BOT))
        adapter.notifyItemInserted(listaMensagens.size - 1)
        scrollToBottom() // Usar método personalizado para scroll
    }

    /**
     * Método para garantir que o scroll vá para o final da lista de mensagens
     * com um pequeno atraso para garantir que a UI tenha tempo de atualizar
     */
    private fun scrollToBottom() {
        // Primeiro, tenta rolar imediatamente
        recyclerViewMensagens.scrollToPosition(listaMensagens.size - 1)

        // Depois, agenda um segundo scroll com um pequeno atraso para garantir
        // que a UI tenha tempo de renderizar a nova mensagem
        recyclerViewMensagens.post {
            recyclerViewMensagens.smoothScrollToPosition(listaMensagens.size - 1)
        }
    }
}
