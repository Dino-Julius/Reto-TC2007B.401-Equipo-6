package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.equipo6.proyectoapp.viewmodel.ChatBotViewModel

@Composable
fun ChatBotView(chatBotVM: ChatBotViewModel) {
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Display chat history
        Column(modifier = Modifier.weight(1f).padding(bottom = 16.dp)) {
            chatHistory.forEach { message ->
                Text(text = message)
            }
        }

        // Input field for user message
        BasicTextField(
            value = userInput,
            onValueChange = { userInput = it },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Send button
        Button(onClick = {
            chatBotVM.sendMessage2(
                content = userInput,
                onFailure = { /* Handle failure */ },
                onStream = { response ->
                    chatHistory = chatHistory + "Bot: $response"
                },
                onFullResponse = { /* Handle full response */ }
            )
            chatHistory = chatHistory + "You: $userInput"
            userInput = ""
        }) {
            Text("Send")
        }
    }
}