package mx.equipo6.proyectoapp.model.profileData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.api.RetrofitClient

data class SignUpRequest(
    val first_name: String,
    val last_name: String,
    val birth_date: String,
    val gender: String,
    val phone: String,
    val email: String,
    val password: String,
    val shipping_address: String
)

data class SignUpResponse(
    val success: Boolean,
    val message: String,
    val user: User
)

data class User(
    val user_id: String,
    val first_name: String,
    val last_name: String,
    val birth_date: String,
    val gender: String,
    val phone: String,
    val email: String,
    val password: String,
    val profile_pic: String,
    val shipping_address: String
)

suspend fun sendDataToServer(signUpRequest: SignUpRequest): String {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.signUp(signUpRequest)
            if (response.success) {
                "Data sent successfully: ${response.message}"
            } else {
                "Error sending data: ${response.message}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Exception occurred: ${e.message}"
        }
    }
}