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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.equipo6.proyectoapp.view.AppPrincipal
import mx.equipo6.proyectoapp.view.ForgotPasswordView
import mx.equipo6.proyectoapp.view.LoginView
import mx.equipo6.proyectoapp.view.SignUpView
import mx.equipo6.proyectoapp.view.Windows
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
            val navController = rememberNavController()  // Initialize NavController

            var showSignUp by remember { mutableStateOf(false) }
            var loggedIn by remember { mutableStateOf(isLoggedIn) }

            if (isFirstRun) {
                sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
            }

            if (loggedIn) {
                AppPrincipal()
            } else {
                NavHost(
                    navController = navController,
                    startDestination = if (showSignUp) "signup" else "login"
                ) {
                    // Login Screen
                    composable("login") {
                        val loginViewModel: LoginViewModel = viewModel()
                        LoginView(
                            onSignUp = {
                                navController.navigate("signup")  // Navigate to signup
                            },
                            onForgotPassword = {
                                // Navigate to ForgotPasswordView
                                navController.navigate("forgotPassword")  // Navigate to forgotPassword
                            },
                            viewModel = loginViewModel
                        )

                        if (loginViewModel.loggedIn.value) {
                            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                            loggedIn = true
                        }
                    }

                    // Signup Screen
                    composable("signup") {
                        val signUpViewModel: SignUpViewModel = viewModel()
                        SignUpView(
                            onLogin = {
                                navController.popBackStack()  // Navigate back to login
                            },
                            viewModel = signUpViewModel
                        )

                        if (signUpViewModel.signedUp.value) {
                            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                            loggedIn = true
                        }
                    }

                    // Forgot Password Screen
                    composable("forgotPassword") {
                        val signUpViewModel: SignUpViewModel = viewModel()
                        ForgotPasswordView(
                            navController = navController,
                            signUpViewModel
                        )
                    }
                }
            }
        }
    }
}
