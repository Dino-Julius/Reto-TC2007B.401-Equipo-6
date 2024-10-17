package mx.equipo6.proyectoapp.viewmodel

import android.util.Log
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

    private var signUpViewModel: SignUpViewModel? = null

    fun setSignUpViewModel(viewModel: SignUpViewModel) {
        signUpViewModel = viewModel
    }

    fun passwordLogin() {
        if (email.value.isNotBlank() && password.value.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val loginRequest = LoginRequest(password.value)
                    val response = RetrofitClient.apiService.verifyPassword(email.value, loginRequest)
                    if (response != null) {
                        loggedIn.value = true
                        getUserByEmail(email.value)
                        Log.d("LoginViewModel", "${response}")
                    } else {
                        Log.d("LoginViewModel", "Invalid email or password")
                        errorMessage.value = "Invalid email or password"
                    }
                } catch (e: Exception) {
                    Log.d("LoginViewModel", e.message.toString())
                    errorMessage.value = "Invalid email or password"
                }
            }
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            try {
                val user = RetrofitClient.apiService.getUserByEmail(email)
                if (user != null) {
                    SignUpViewModel.copyUserData(user)
                    Log.d("LoginViewModel", "User fetched: ${user}")
                } else {
                    Log.d("LoginViewModel", "User not found")
                }
            } catch (e: Exception) {
                Log.d("LoginViewModel", e.message.toString())
            }
        }
    }
}
