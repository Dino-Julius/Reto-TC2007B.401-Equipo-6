package mx.equipo6.proyectoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.NSChatBot
import java.io.IOException
import kotlinx.coroutines.Job

class ChatBotViewModel : ViewModel() {
    private var enchatbot = NSChatBot()

    // Track the current job to cancel previous requests
    private var currentJob: Job? = null

    var isSending: Boolean? = null // Inicializa isSending a false

    init {
        enchatbot.initialize("http://161.35.96.129:5000/chat")
    }

    /**
     * @autor: Manuel Olmos Antillón
     * @param: content: String
     * @param: onFailure: (IOException) -> Unit
     * @param: onResponse: (String) -> Unit
     * Función para enviar un mensaje al chatbot y manejar la respuesta
     */
    fun sendMessage2(
        content: String,
        onFailure: (IOException) -> Unit,
        onResponse: (String) -> Unit // Callback para recibir la respuesta completa
    ) {
        Log.d("ChatBotViewModel", "Sending message: $content")

        // Cancel the previous job if it's still active
        currentJob?.cancel()

        // Start a new job for the latest request
        currentJob = viewModelScope.launch {
            enchatbot.sendMessage(
                content,
                onFailure = {
                    Log.e("ChatBotViewModel", "Request failed", it)
                    isSending = false // Asegúrate de restablecer el estado aquí también
                    onFailure(it)
                },
                onStream = { response ->
                    // Aquí no acumulamos fragmentos, solo tomamos la respuesta completa
                    Log.d("ChatBotViewModel", "Received response: $response")
                    onResponse(response) // Llamamos directamente con la respuesta
                },
                onCompleted = {
                    Log.d("ChatBotViewModel", "Request completed.")
                    isSending = false // Asegúrate de restablecer el estado aquí también
                }
            )
        }
    }
}