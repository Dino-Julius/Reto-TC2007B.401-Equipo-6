package mx.equipo6.proyectoapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase sellada que contiene las pantallas de la aplicación.
 * @author Equipo 6
 * @property route Ruta de la pantalla.
 * @property etiqueta Etiqueta de la pantalla.
 * @property icono Icono de la pantalla.
 */
sealed class Windows(
    val route: String,
    val etiqueta: String,
    val icono: ImageVector
) {
    // Definir miembros de clase estáticos
    companion object {
        var listaPantallas = listOf(HomeView, AboutUsView, ComunityView, StoreView)
        // Constante en tiempo de compilación (#Define)
        const val ROUTE_HOME = "Inicio"
        const val ROUTE_ABOUTUS = "Nosotr@s"
        const val ROUTE_COMUNITY = "Comunidad"
        // const val ROUTE_CONFIG = "Configuración"
        const val ROUTE_CHATBOT = "ChatBot"
        const val ROUTE_CALENDAR = "Calendario"
        const val ROUTE_STORE = "Tienda"
    }

    private data object HomeView : Windows(
        ROUTE_HOME,
        "HOME",
        Icons.Default.Home
    )

    private data object AboutUsView : Windows(
        ROUTE_ABOUTUS,
        "Nosotr@s",
        Icons.Default.ThumbUp
    )

    private data object ComunityView : Windows(
        ROUTE_COMUNITY,
        "Comunidad",
        Icons.Default.Person
    )

//    private data object ConfigView : Windows(
//        ROUTE_CONFIG,
//        "Configuración",
//        Icons.Default.Person
//    )

    private data object ChatBotView : Windows(
        ROUTE_CHATBOT,
        "ChatBot",
        Icons.Default.Person
    )

    private data object CalendarView : Windows(
        ROUTE_CALENDAR,
        "Calendario",
        Icons.Default.Person
    )

    private data object StoreView : Windows(
        ROUTE_STORE,
        "Tienda",
        Icons.Default.Star
    )
}