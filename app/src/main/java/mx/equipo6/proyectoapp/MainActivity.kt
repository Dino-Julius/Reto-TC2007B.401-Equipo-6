package mx.equipo6.proyectoapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.equipo6.proyectoapp.view.AppPrincipal
import mx.equipo6.proyectoapp.view.LoginView
import mx.equipo6.proyectoapp.view.SignUpView
import mx.equipo6.proyectoapp.viewmodel.LoginViewModel
import mx.equipo6.proyectoapp.viewmodel.SignUpViewModel

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

            if (isFirstRun) {
                sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
            }

            if (loggedIn) {
                AppPrincipal()
            } else {
                if (showSignUp) {
                    val signUpViewModel: SignUpViewModel = viewModel()
                    SignUpView(
                        onLogin = {
                            showSignUp = false
                        },
                        viewModel = signUpViewModel
                    )
                    if (signUpViewModel.signedUp.value) {
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        loggedIn = true
                    }
                } else {
                    val loginViewModel: LoginViewModel = viewModel()
                    LoginView(
                        onSignUp = {
                            showSignUp = true
                        },
                        viewModel = loginViewModel
                    )
                    if (loginViewModel.loggedIn.value) {
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        loggedIn = true
                    }
                }
            }
        }
    }
}