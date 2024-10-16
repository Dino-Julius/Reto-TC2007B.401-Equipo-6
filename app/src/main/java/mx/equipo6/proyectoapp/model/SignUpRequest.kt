package mx.equipo6.proyectoapp.model.auth

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: String,
    val phone: String,
    val email: String,
    val password: String,
    val address: String
)