package mx.equipo6.proyectoapp.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.model.profileData.createJsonData
import mx.equipo6.proyectoapp.model.profileData.sendDataToServer
import java.net.HttpURLConnection
import java.net.URL

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("user_prefs", Application.MODE_PRIVATE)

    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    var birthDate = mutableStateOf("")
    var gender = mutableStateOf("")
    var phone = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var address = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)
    var signedUp = mutableStateOf(false)
    var expanded = mutableStateOf(false)

    init {
        loadUserData()
    }

    fun signUp() {
        if (firstName.value.isNotBlank() && lastName.value.isNotBlank() && birthDate.value.isNotBlank() && gender.value.isNotBlank() && phone.value.isNotBlank() && email.value.isNotBlank() && address.value.isNotBlank() && password.value.isNotBlank() && confirmPassword.value.isNotBlank()) {
            if (password.value == confirmPassword.value) {
                viewModelScope.launch {
                    saveUserData() // Save user data on sign up
                    signedUp.value = true
                }
            } else {
                errorMessage.value = "Passwords do not match"
            }
        } else {
            errorMessage.value = "Please fill in all fields"
        }
    }

    fun saveUserData() {
        sharedPreferences.edit().apply {
            putString("first_name", firstName.value)
            putString("last_name", lastName.value)
            putString("birth_date", birthDate.value)
            putString("gender", gender.value)
            putString("phone", phone.value)
            putString("email", email.value)
            putString("address", address.value)
            putString("password", password.value)
            apply()
        }
        val jsonData = createJsonData(
            firstName.value,
            lastName.value,
            birthDate.value,
            gender.value,
            phone.value,
            email.value,
            address.value,
            password.value
        )

        viewModelScope.launch {
            val result = sendDataToServer(jsonData)
            println(result)
        }
    }

    private fun loadUserData() {
        firstName.value = sharedPreferences.getString("first_name", "") ?: ""
        lastName.value = sharedPreferences.getString("last_name", "") ?: ""
        birthDate.value = sharedPreferences.getString("birth_date", "") ?: ""
        gender.value = sharedPreferences.getString("gender", "") ?: ""
        phone.value = sharedPreferences.getString("phone", "") ?: ""
        email.value = sharedPreferences.getString("email", "") ?: ""
        address.value = sharedPreferences.getString("address", "") ?: ""
        password.value = sharedPreferences.getString("password", "") ?: ""
    }

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
        firstName.value = ""
        lastName.value = ""
        birthDate.value = ""
        gender.value = ""
        phone.value = ""
        email.value = ""
        password.value = ""
        confirmPassword.value = ""
        address.value = ""
    }

    fun sendResetLink() {
        if (email.value.isBlank()) {
            errorMessage.value = "Por favor, ingresa tu correo electrónico"
            return
        }

        errorMessage.value = null

        viewModelScope.launch {
            val jsonData = createResetLinkJson(email.value)
            val response = sendResetLinkToServer(jsonData)

            if (response.startsWith("Error")) {
                errorMessage.value = response
            } else {
                errorMessage.value = "Enlace de restablecimiento enviado correctamente"
            }
        }
    }

    private fun createResetLinkJson(email: String): String {
        return """
            {
                "email": "$email"
            }
        """.trimIndent()
    }

    private suspend fun sendResetLinkToServer(jsonData: String): String {
        return withContext(context = Dispatchers.IO) {
            try {
                val url = URL("http://yourserver.com/api/reset-password") //TODO Replace with your server URL
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    doOutput = true
                    setRequestProperty("Content-Type", "application/json")
                }

                // Write JSON data to the output stream
                connection.outputStream.use { outputStream ->
                    outputStream.write(jsonData.toByteArray())
                }

                // Check response code from the server
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    "Enlace de restablecimiento enviado correctamente."
                } else {
                    "Error al enviar el enlace: $responseCode"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                "Error: ${e.message}"
            }
        }
    }
}
