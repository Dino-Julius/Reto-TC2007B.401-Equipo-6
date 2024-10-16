package mx.equipo6.proyectoapp.model

fun validateCash(input: String): Boolean {
    // Regular expression for a number with optional two decimal places
    val regex = """^\d+(\.\d{1,2})?$""".toRegex()

    return regex.matches(input)
}
