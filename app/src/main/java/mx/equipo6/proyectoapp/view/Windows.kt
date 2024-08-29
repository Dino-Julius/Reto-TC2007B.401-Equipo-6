package mx.equipo6.proyectoapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase sellada que contiene las pantallas de la aplicación.
 */
sealed class Windows(
    val route: String,
    val etiqueta: String,
    val icono: ImageVector
) {
    // Definir miembros de clase estáticos
    companion object {
        var listaPantallas = listOf(AboutUsView, ComunityView, HomeView)
        // Constante en tiempo de compilación (#Define)
        const val ROUTE_HOME = "Inicio"
        const val ROUTE_ABOUTUS = "¿Quiénes Somos?"
        const val ROUTE_COMUNITY = "Comunidad"
        const val ROUTE_CONFIG = "Configuración"
        const val ROUTE_CHATBOT = "ChatBot"
        const val ROUTE_CALENDAR = "Calendario"
        const val ROUTE_STORE = "Tienda"
    }

    private data object AboutUsView : Windows(
        ROUTE_ABOUTUS,
        "¿Quiénes Somos?",
        Icons.Default.ThumbUp
    )

    private data object ComunityView : Windows(
        ROUTE_COMUNITY,
        "Comunidad",
        Icons.Default.Person
    )

    private data object HomeView : Windows(
        ROUTE_HOME,
        "HOME",
        Icons.Default.Home
    )
}