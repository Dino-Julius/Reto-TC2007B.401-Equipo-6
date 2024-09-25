package mx.equipo6.proyectoapp.model

import kotlinx.coroutines.TimeoutCancellationException
import java.io.IOException

class ChatBotModel {
    private suspend fun getSummaryFromScript(question: String): String{
        var chatAnswer = ""
        try {
            val process = Runtime.getRuntime().exec("python3 src/main/python/chatbot.py $question")
            var outputLine: String? = ""
            while (outputLine != null) {
                outputLine = process.inputStream.bufferedReader().readLine()
                if (outputLine != null) {
                    chatAnswer += outputLine
                }
            }
        }
        // IOException: Error de entrada/salida
        catch (e: IOException) {
            e.printStackTrace()
            throw IOException("Error al obtener la respuesta del chatbot: ${e.message}")
        }
        // TimeoutCancellationException: Error de tiempo de espera
        catch (e: TimeoutCancellationException) {
            e.printStackTrace()
            throw Exception("Error al obtener la respuesta del chatbot: ${e.message}")
        }
        // Exception: Error general
        catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error al obtener la respuesta del chatbot: ${e.message}")
        }
        return chatAnswer
    }
}