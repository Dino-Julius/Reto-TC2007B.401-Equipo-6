package mx.equipo6.proyectoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
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

    fun signUp() {
        if (firstName.value.isNotBlank() && lastName.value.isNotBlank() && birthDate.value.isNotBlank() && gender.value.isNotBlank() && phone.value.isNotBlank() && email.value.isNotBlank() && address.value.isNotBlank() && password.value.isNotBlank() && confirmPassword.value.isNotBlank()) {
            if (password.value == confirmPassword.value) {
                viewModelScope.launch {
                    // Simulate sign up process
                    signedUp.value = true
                }
            } else {
                errorMessage.value = "Passwords do not match"
            }
        } else {
            errorMessage.value = "Please fill in all fields"
        }
    }
}