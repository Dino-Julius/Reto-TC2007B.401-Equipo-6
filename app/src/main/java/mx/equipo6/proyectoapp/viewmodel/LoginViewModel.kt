package mx.equipo6.proyectoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.api.RetrofitClient
import mx.equipo6.proyectoapp.model.auth.LoginRequest

class LoginViewModel : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)
    var loggedIn = mutableStateOf(false)

    fun passwordlogin() {
        if (email.value.isNotBlank() && password.value.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val loginRequest = LoginRequest(email.value, password.value)
                    val response = RetrofitClient.apiService.verifyPassword(email.value, loginRequest)
                    if (response != null) {
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