package mx.equipo6.proyectoapp.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
//import me.bush.translator.Language
import android.util.Log

class NSChatBot {
    private var apiUrl: String = ""
    private val chatBotMessages = mutableListOf<Map<String, String>>()
    private val client = okhttp3.OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    fun initialize(apiUrl: String) {
        this.apiUrl = apiUrl
    }

    suspend fun sendMessage(message: String, onStream: (String) -> Unit, onFailure: (IOException) -> Unit, onCompleted: () -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                addMessage("user", message)
                val request = buildRequest()
                Log.d("NSChatBot", "Sending request to $apiUrl with message: $message")
                val response = executeRequest(request)
                handleResponse(response, onStream, onCompleted)
            } catch (e: IOException) {
                handleFailure(e, onFailure)
            }
        }
    }

    private fun addMessage(role: String, content: String) {
        chatBotMessages.add(mapOf("role" to role, "content" to content))
    }

    private fun buildRequest(): okhttp3.Request {
        val json = com.google.gson.Gson().toJson(mapOf("messages" to chatBotMessages))
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        return okhttp3.Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .build()
    }

    private fun executeRequest(request: okhttp3.Request): okhttp3.Response {
        return client.newCall(request).execute()
    }

    private suspend fun handleResponse(response: okhttp3.Response, onStream: (String) -> Unit, onCompleted: () -> Unit) {
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        var fullResponse = ""
        val source = response.body?.source()
        source?.let {
            while (!it.exhausted()) {
                val line = it.readUtf8Line()
                fullResponse += line.orEmpty()
                onStream(fullResponse)
            }
            onCompleted()
        }
        addMessage("assistant", fullResponse)
    }

    private suspend fun handleFailure(e: IOException, onFailure: (IOException) -> Unit) {
        withContext(Dispatchers.Main) {
            onFailure(e)
        }
    }
}