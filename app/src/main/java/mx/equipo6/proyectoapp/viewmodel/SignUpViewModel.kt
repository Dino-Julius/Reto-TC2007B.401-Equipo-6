package mx.equipo6.proyectoapp.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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
}
