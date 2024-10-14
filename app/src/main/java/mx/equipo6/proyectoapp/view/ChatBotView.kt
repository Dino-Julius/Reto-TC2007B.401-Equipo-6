package mx.equipo6.proyectoapp.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import mx.equipo6.proyectoapp.viewmodel.ChatBotViewModel

@Composable
fun ChatBotView(chatBotVM: ChatBotViewModel) {
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) } // Almacena el historial de mensajes
    var isSending by remember { mutableStateOf(false) } // Estado para controlar si hay una solicitud en curso
    var errorMessage by remember { mutableStateOf<String?>(null) } // Estado para el mensaje de error

    // State for coroutine scope
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState() // State for scrolling

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Mostrar instrucciones si no hay mensajes en el historial
        if (chatHistory.isEmpty()) {
            Text(
                text = "Este es el chatbot de Zazil para contestar cualquier pregunta sobre la menstruación. Pregúntame cualquier cosa cuando gustes.",
                color = Color.Gray,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            // Sección de chat
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {
                items(chatHistory.size) { index ->
                    val (message, isUser) = chatHistory[index]
                    ChatBubble(message = message, isUser = isUser)
                }
            }
        }

        // Mostrar animación de "escribiendo" si está enviando
        if (isSending) {
            TypingIndicator()
        }

        // Mostrar mensaje de error si existe
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
        }

        // Row para el campo de entrada y el botón de enviar
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Campo de entrada para el mensaje del usuario
            BasicTextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp) // Espacio entre el campo de texto y el botón
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (!isSending && userInput.isNotBlank()) { // Verifica si no hay una solicitud en curso
                            // Inicia el envío del mensaje
                            Log.d("ChatBotView", "User message: $userInput")
                            chatHistory = chatHistory + Pair("You: $userInput", true) // Agrega el mensaje del usuario
                            isSending = true
                            errorMessage = null // Limpiar mensaje de error
                            chatBotVM.sendMessage2(
                                content = userInput,
                                onFailure = {
                                    Log.e("ChatBotView", "Failed to send message", it)
                                    isSending = false // Asegúrate de restablecer el estado aquí también
                                    errorMessage = "Error: ${it.message}" // Mostrar mensaje de error
                                },
                                onResponse = { response ->
                                    Log.d("ChatBotView", "Bot response: $response")
                                    chatHistory = chatHistory + Pair("Bot: $response", false) // Agrega la respuesta del bot
                                    isSending = false // Restablece el estado después de la respuesta
                                }
                            )
                            userInput = "" // Limpia el campo de entrada
                            focusManager.clearFocus() // Oculta el teclado
                        }
                    }
                ),
                decorationBox = { innerTextField ->
                    if (userInput.isEmpty()) {
                        Text(
                            text = "Escribir...",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}


@Composable
fun ChatBubble(message: String, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Espaciado entre las burbujas
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (isUser) Color(0xFFC7A8BC) else Color(0xFFFFFFFF))
                .padding(12.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(
                text = message,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = if (isUser) TextAlign.End else TextAlign.Start
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.Start) {
        CircularProgressIndicator(
            color = Color(0xFFC7A8BC), // Color del indicador de carga
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Escribiendo...", fontSize = 14.sp, color = Color.Gray)
    }
}
