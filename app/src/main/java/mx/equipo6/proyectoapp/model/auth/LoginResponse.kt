package mx.equipo6.proyectoapp.model.auth

/**
 * Data class que captura la respuesta del servidor al intentar loguear a un usuario
 * @param success true si el login fue exitoso, false en caso contrario
 * @param message mensaje que se muestra al usuario
 * @author Julio Vivas | A01749879
 */
data class LoginResponse(val success: Boolean, val message: String)