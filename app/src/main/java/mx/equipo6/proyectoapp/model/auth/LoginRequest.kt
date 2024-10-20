package mx.equipo6.proyectoapp.model.auth

/**
 * Data class que captura la información necesaria para realizar una petición de login
 * @param password Contraseña del usuario
 * @author Julio Vivas | A01749879
 */
data class LoginRequest(val password: String)