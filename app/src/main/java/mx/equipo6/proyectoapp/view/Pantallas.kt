package mx.equipo6.proyectoapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase sellada que contiene las pantallas de la aplicación.
 */
sealed class Pantallas(
    val ruta: String,
    val etiqueta: String,
    val icono: ImageVector
) {
    // Definir miembros de clase estáticos
    companion object {
        var listaPantallas = listOf(AboutUsView, ComunityView, HomeView)
        // Constante en tiempo de compilación (#Define)
        const val ROUTE_HOME = "Home"
        const val ROUTE_ABOUTUS = "¿Quiénes Somos?"
        const val ROUTE_COMUNITY = "Comunidad"
    }

    private data object AboutUsView : Pantallas(
        ROUTE_ABOUTUS,
        "¿Quiénes Somos?",
        Icons.Default.ThumbUp
    )

    private data object ComunityView : Pantallas(
        ROUTE_COMUNITY,
        "Comunidad",
        Icons.Default.Person
    )

    private data object HomeView : Pantallas(
        ROUTE_HOME,
        "HOME",
        Icons.Default.Home
    )
}