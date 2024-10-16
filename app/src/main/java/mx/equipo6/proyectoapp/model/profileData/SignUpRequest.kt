package mx.equipo6.proyectoapp.model.profileData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

fun createJsonData(
    firstName: String,
    lastName: String,
    birthDate: String,
    gender: String,
    phone: String,
    email: String,
    address: String,
    password: String
): String {
    return """
        {
            "first_name": "$firstName",
            "last_name": "$lastName",
            "birth_date": "$birthDate",
            "gender": "$gender",
            "phone": "$phone",
            "email": "$email",
            "address": "$address",
            "password": "$password"
        }
    """.trimIndent()
}

suspend fun sendDataToServer(jsonData: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("http://104.248.55.22:3000/api/users")
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                doOutput = true
                setRequestProperty("Content-Type", "application/json")
            }

            connection.outputStream.use { outputStream ->
                outputStream.write(jsonData.toByteArray())
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                "Data sent successfully."
            } else {
                "Error sending data: $responseCode"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Exception occurred: ${e.message}"
        }
    }
}
