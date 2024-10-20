package mx.equipo6.proyectoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.api.RetrofitClient
import mx.equipo6.proyectoapp.model.auth.LoginRequest
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

/**
 * ViewModel para la pantalla de login
 * @author Jesús Guzmán | A01799257
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf<String?>(null)
    var loggedIn = mutableStateOf(false)

    private var signUpViewModel: SignUpViewModel? = null

    init {
        // Cargar el email de las SharedPreferences cuando se crea el ViewModel
        email.value = getEmailFromPreferences()
    }

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
                        saveEmailToPreferences(email.value)  // Save email when login is successful
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

    // Guardar el email en las SharedPreferences
    private fun saveEmailToPreferences(email: String) {
        sharedPreferences.edit().putString("email", email).apply()
    }

    // Obtener el email de las SharedPreferences
    private fun getEmailFromPreferences(): String {
        return sharedPreferences.getString("email", "") ?: ""
    }
}
