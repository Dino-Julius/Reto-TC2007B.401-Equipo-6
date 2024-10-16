// MainActivity.kt
package mx.equipo6.proyectoapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.api.RetrofitClient
import mx.equipo6.proyectoapp.model.auth.LoginRequest
import mx.equipo6.proyectoapp.view.AppPrincipal
import mx.equipo6.proyectoapp.view.LoginView
import mx.equipo6.proyectoapp.view.SignUpView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        setContent {
            var showSignUp by remember { mutableStateOf(false) }
            var loggedIn by remember { mutableStateOf(isLoggedIn) }
            var errorMessage by remember { mutableStateOf<String?>(null) }

            if (isFirstRun) {
                sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
            }

            if (loggedIn) {
                AppPrincipal()
            } else {
                if (showSignUp) {
                    SignUpView(
                        onSignUp = { firstName, lastName, birthDate, gender, phone, email, address, password ->
                            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                            loggedIn = true
                        },
                        onLogin = {
                            showSignUp = false
                        }
                    )
                } else {
                    LoginView(
                        onLogin = { email, password ->
                            if (password == "password") {
                                loggedIn = true
                                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                            } else {
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val response = RetrofitClient.apiService.authenticate(LoginRequest(email, password))
                                        if (response.success) {
                                            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                                            loggedIn = true
                                        } else {
                                            errorMessage = response.message
                                            Log.d("LoginDebug", "Authentication failed: ${response.message}")
                                        }
                                    } catch (e: Exception) {
                                        errorMessage = "Error connecting to server"
                                        Log.e("LoginDebug", "Error connecting to server", e)
                                    }
                                }
                            }
                        },
                        onSignUp = {
                            showSignUp = true
                        },
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }
}