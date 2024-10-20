package mx.equipo6.proyectoapp.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import android.util.Log

/**
 * @author: Manuel Olmos Antillón | A01750748
 * Clase para manejar la comunicación con el chatbot
 */
class NSChatBot {
    private var apiUrl: String = ""
    private val chatBotMessages = mutableListOf<Map<String, String>>()
    private val client = okhttp3.OkHttpClient.Builder()
        .connectTimeout(5, java.util.concurrent.TimeUnit.MINUTES)
        .writeTimeout(5, java.util.concurrent.TimeUnit.MINUTES)
        .readTimeout(5, java.util.concurrent.TimeUnit.MINUTES)
        .build()

    /**
     * @author: Manuel Olmos Antillón | A01750748
     * @param apiUrl: String
     * Función para inicializar el chatbot con la URL de la API
     */
    fun initialize(apiUrl: String) {
        this.apiUrl = apiUrl
    }

    /**
     * @author Manuel Olmos Antillón | A01750748
     * @param message: String
     * @param onStream: (String) -> Unit
     * @param onFailure: (IOException) -> Unit
     * @param onCompleted: () -> Unit
     * Función para enviar un mensaje al chatbot
     */
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

    /**
     * @author Manuel Olmos Antillón | A01750748
     * @param role: String
     * @param content: String
     * Función para agregar un mensaje a la lista de mensajes del chatbot
     */
    private fun addMessage(role: String, content: String) {
        chatBotMessages.add(mapOf("role" to role, "content" to content))
    }

    /**
     * @author Manuel Olmos Antillón | A01750748
     * @return okhttp3.Request
     * Función para construir la solicitud HTTP para enviar al chatbot
     */
    private fun buildRequest(): okhttp3.Request {
        val json = com.google.gson.Gson().toJson(mapOf("messages" to chatBotMessages))
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        return okhttp3.Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .build()
    }

    /**
     * Función para ejecutar la solicitud HTTP
     * @author Manuel Olmos Antillón | A01750748
     * @param request: okhttp3.Request
     * @return okhttp3.Response
     */
    private fun executeRequest(request: okhttp3.Request): okhttp3.Response {
        return client.newCall(request).execute()
    }

    /**
     * @author Manuel Olmos Antillón | A01750748
     * @param response okhttp3.Response
     * @param onStream (String) -> Unit
     * @param onCompleted () -> Unit
     * Función para manejar la respuesta del chatbot
     */
    private suspend fun handleResponse(response: okhttp3.Response, onStream: (String) -> Unit, onCompleted: () -> Unit) {
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        val fullResponse = StringBuilder()  // Usamos StringBuilder para construir la respuesta completa
        val source = response.body?.source()
        source?.let {
            while (!it.exhausted()) {
                val line = it.readUtf8Line()
                line?.let { fullResponse.append(it) } // Acumula las líneas en lugar de llamar a onStream
            }
            // Aquí solo llama a onStream si necesitas un flujo de actualización, en este caso se puede omitir
            addMessage("assistant", fullResponse.toString())  // Añade la respuesta completa
        }
        onStream(fullResponse.toString())  // Llama a onStream solo con la respuesta completa
        onCompleted()  // Indica que la respuesta ha sido procesada
    }

    /**
     * @author Manuel Olmos Antillón | A01750748
     * @param e: IOException
     * @param onFailure: (IOException) -> Unit
     * Función para manejar fallos en la solicitud HTTP
     */
    private suspend fun handleFailure(e: IOException, onFailure: (IOException) -> Unit) {
        withContext(Dispatchers.Main) {
            onFailure(e)
        }
    }
}