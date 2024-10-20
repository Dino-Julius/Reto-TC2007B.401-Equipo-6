package mx.equipo6.proyectoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.NSChatBot
import java.io.IOException
import kotlinx.coroutines.Job
import mx.equipo6.proyectoapp.include.Constants.Companion.CHAT_URL

class ChatBotViewModel : ViewModel() {
    // Instancia de ChatBot
    private var enchatbot = NSChatBot()

    // Seguimiento del trabajo actual para cancelar las solicitudes anteriores
    private var currentJob: Job? = null

    // Variable para rastrear si se está enviando un mensaje actualmente
    var isSending: Boolean? = null

    // Inicialización de la instancia de ChatBot
    init {
        enchatbot.initialize(CHAT_URL)
    }

    /**
     * @autor Manuel Olmos Antillón | A01750748
     * @param content: String
     * @param onFailure: (IOException) -> Unit
     * @param onResponse: (String) -> Unit
     * Función para enviar un mensaje al chatbot y manejar la respuesta
     */
    fun sendMessage2(
        content: String,
        onFailure: (IOException) -> Unit,
        onResponse: (String) -> Unit
    ) {
        Log.d("ChatBotViewModel", "Sending message: $content")

        // Cancela el trabajo anterior si aún está activo
        currentJob?.cancel()

        // Almacenamos el trabajo actual para poder cancelarlo si es necesario
        currentJob = viewModelScope.launch {
            enchatbot.sendMessage(
                content,
                onFailure = {
                    Log.e("ChatBotViewModel", "Request failed", it)
                    isSending = false
                    onFailure(it)
                },
                onStream = { response ->
                    // Aquí no acumulamos fragmentos, solo tomamos la respuesta completa
                    Log.d("ChatBotViewModel", "Received response: $response")
                    onResponse(response)
                },
                onCompleted = {
                    Log.d("ChatBotViewModel", "Request completed.")
                    isSending = false
                }
            )
        }
    }
}