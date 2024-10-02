package mx.equipo6.proyectoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.NSChatBot
import java.io.IOException

class ChatBotViewModel : ViewModel() {
    private var enchatbot = NSChatBot()

    init {
        enchatbot.initialize("http://192.168.1.10:5000/chat" /*, Language.TURKISH*/)
    }

    fun sendMessage2(content: String, onFailure: (IOException) -> Unit, onStream: (String) -> Unit, onFullResponse: () -> Unit) {
        Log.d("ChatBotViewModel", "Sending message: $content")
        viewModelScope.launch {
            enchatbot.sendMessage(
                content,
                onStream = {
                    Log.d("ChatBotViewModel", "Received stream: $it")
                    onStream(it)
                },
                onFailure = {
                    Log.e("ChatBotViewModel", "Request failed", it)
                    onFailure(it)
                },
                onCompleted = {
                    Log.d("ChatBotViewModel", "Request completed")
                    onFullResponse()
                }
            )
        }
    }

}