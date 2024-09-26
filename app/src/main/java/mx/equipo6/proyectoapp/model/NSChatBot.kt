package mx.equipo6.proyectoapp.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
//import me.bush.translator.Language

class NSChatBot {
    private var apiUrl: String = ""
    //private var language: me.bush.translator.Language = me.bush.translator.Language.ENGLISH
    //private val translator = me.bush.translator.Translator()
    private val chatBotMessages = mutableListOf<Map<String, String>>()
    private val client = okhttp3.OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    fun initialize(apiUrl: String, /*language: me.bush.translator.Language*/) {
        this.apiUrl = apiUrl
        //this.language = language
    }

    suspend fun sendMessage(message: String, onStream: (String) -> Unit, onFailure: (IOException) -> Unit, onCompleted: () -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                //val translatedMessage = translateMessage(message)
                //addMessage("user", translatedMessage)
                addMessage("user", message)

                val request = buildRequest()
                val response = executeRequest(request)

                handleResponse(response, onStream, onCompleted)
            } catch (e: IOException) {
                handleFailure(e, onFailure)
            }
        }
    }

    //private suspend fun translateMessage(message: String): String {
    //    return translator.translate(message, me.bush.translator.Language.ENGLISH, language).translatedText
    //}

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
                //val translatedMessage = translator.translate(fullResponse, language, me.bush.translator.Language.ENGLISH).translatedText
                //withContext(Dispatchers.Main) {
                //    onStream(translatedMessage)
                //}
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