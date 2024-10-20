package mx.equipo6.proyectoapp.model

/**
 * Función que valida si la entrada es un valor de efectivo válido.
 * @param input el valor de efectivo a validar
 * @return true si el valor de efectivo es válido, false en caso contrario
 * @author Jesús Guzmán | A01799257
 */
fun validateCash(input: String): Boolean {
    // Regular expression for a number with optional two decimal places
    val regex = """^\d+(\.\d{1,2})?$""".toRegex()

    return regex.matches(input)
}
