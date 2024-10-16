package mx.equipo6.proyectoapp.model.auth

data class LoginRequest(
    val email: String,
    val password: String
)