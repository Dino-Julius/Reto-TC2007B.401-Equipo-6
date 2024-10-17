package mx.equipo6.proyectoapp.model.profileData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.api.RetrofitClient

data class EditProfileRequest(
    val first_name: String?,
    val last_name: String?,
    val birth_date: String?,
    val gender: String?,
    val phone: String?,
    val email: String?,
    val password: String?,
    val shipping_address: String?,
    val profile_pic: String?
)

data class EditProfileResponse(
    val success: Boolean,
    val message: String,
    val user: User
)

suspend fun sendDataToServer(editProfileRequest: EditProfileRequest): String {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.updateUser(editProfileRequest.email!!, editProfileRequest)
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