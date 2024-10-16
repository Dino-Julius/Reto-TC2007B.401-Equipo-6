package mx.equipo6.proyectoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.api.RetrofitClient
import mx.equipo6.proyectoapp.model.auth.User

class LoginViewModel : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)
    var loggedIn = mutableStateOf(false)

    fun passwordlogin() {
        if (email.value.isNotBlank() && password.value.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val user = RetrofitClient.apiService.getUserByEmail(email.value)
                    if (user != null && password.value == "password") {
                        loggedIn.value = true
                    } else {
                        errorMessage.value = "Invalid email or password"
                    }
                } catch (e: Exception) {
                    errorMessage.value = "Invalid email or password"
                }
            }
        }
    }
}