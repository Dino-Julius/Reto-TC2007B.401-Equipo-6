package mx.equipo6.proyectoapp.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.api.RetrofitClient
import mx.equipo6.proyectoapp.model.auth.User
import mx.equipo6.proyectoapp.model.profileData.SignUpRequest
import mx.equipo6.proyectoapp.model.profileData.sendDataToServer

/**
 * Data class que contiene la información del correo electrónico
 * @param email Correo electrónico
 * @author Jesús Guzmán | A01799257
 */
data class EmailRequest(val email: String)

/**
 * Data class que contiene la información de la respuesta al enviar el enlace de restablecimiento
 * @param success Indica si la operación fue exitosa
 * @param message Mensaje de la respuesta
 * @author Jesús Guzmán | A01799257
 */
data class ResetLinkResponse(val success: Boolean, val message: String)

/**
 * ViewModel para la pantalla de registro
 * @param application Aplicación de Android
 * @property sharedPreferences SharedPreferences para guardar la información del usuario
 * @property firstName Nombre del usuario
 * @property lastName Apellido del usuario
 * @property birthDate Fecha de nacimiento del usuario
 * @param gender Género del usuario
 * @param phone Teléfono del usuario
 * @param email Correo electrónico del usuario
 * @param password Contraseña del usuario
 * @param confirmPassword Confirmación de la contraseña del usuario
 * @param address Dirección del usuario
 * @param profilePic Foto de perfil del usuario
 * @param errorMessage Mensaje de error
 * @param signedUp Indica si el usuario se ha registrado
 * @param expanded Indica si el formulario de registro está expandido
 * @author Jesús Guzmán | A01799257
 */
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
    var profilePic = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)
    var signedUp = mutableStateOf(false)
    var expanded = mutableStateOf(false)

    init {
        loadUserData()
        // Guarda esta instancia en el objeto companion para que pueda ser accedida estáticamente
        Companion.instance = this
    }

    fun signUp() {
        if (firstName.value.isNotBlank() && lastName.value.isNotBlank() && birthDate.value.isNotBlank() && gender.value.isNotBlank() && phone.value.isNotBlank() && email.value.isNotBlank() && address.value.isNotBlank() && password.value.isNotBlank() && confirmPassword.value.isNotBlank()) {
            if (password.value == confirmPassword.value) {
                viewModelScope.launch {
                    saveUserData()
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

    private fun createJsonData(
        firstName: String,
        lastName: String,
        birthDate: String,
        gender: String,
        phone: String,
        email: String,
        address: String,
        password: String
    ): SignUpRequest {
        return SignUpRequest(
            first_name = firstName,
            last_name = lastName,
            birth_date = birthDate,
            gender = gender,
            phone = phone,
            email = email,
            password = password,
            shipping_address = address
        )
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
            val emailRequest = EmailRequest(email.value)
            val response = sendResetLinkToServer(emailRequest)

            if (response.success) {
                errorMessage.value = "Enlace de restablecimiento enviado correctamente"
            } else {
                errorMessage.value = response.message
            }
        }
    }

    private suspend fun sendResetLinkToServer(emailRequest: EmailRequest): ResetLinkResponse {
        return withContext(context = Dispatchers.IO) {
            try {
                RetrofitClient.apiService.sendResetLink(emailRequest)
            } catch (e: Exception) {
                e.printStackTrace()
                ResetLinkResponse(false, "Error: ${e.message}")
            }
        }
    }

    fun copyUserData(user: User) {
        firstName.value = user.firstName
        lastName.value = user.lastName
        birthDate.value = user.birthDate
        gender.value = user.gender
        phone.value = user.phone
        email.value = user.email
        address.value = user.address
        profilePic.value = user.profilePic
    }

    companion object {
        private var instance: SignUpViewModel? = null

        fun copyUserData(user: User) {
            instance?.let {
                it.firstName.value = user.firstName
                it.lastName.value = user.lastName
                it.birthDate.value = user.birthDate
                it.gender.value = user.gender
                it.phone.value = user.phone
                it.email.value = user.email
                it.address.value = user.address
                it.profilePic.value = user.profilePic
            } ?: run {
                println("SignUpViewModel instance is null")
            }
        }
    }
}
