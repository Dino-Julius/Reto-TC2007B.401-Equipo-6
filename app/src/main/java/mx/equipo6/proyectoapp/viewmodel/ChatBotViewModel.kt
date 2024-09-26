package mx.equipo6.proyectoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.NSChatBot
import java.io.IOException

class ChatBotViewModel : ViewModel() {
    private var enchatbot = NSChatBot()

    init {
        enchatbot.initialize("http://192.168.1.90:5000/chat" /*, Language.TURKISH*/)
    }

    fun sendMessage2(content: String, onFailure: (IOException) -> Unit, onStream: (String) -> Unit, onFullResponse: () -> Unit) {
        viewModelScope.launch {
            enchatbot.sendMessage(
                content,
                onStream = {
                    onStream(it)
                },
                onFailure = {
                    onFailure(it)
                },
                onCompleted = {
                    onFullResponse()
                }
            )
        }
    }
}