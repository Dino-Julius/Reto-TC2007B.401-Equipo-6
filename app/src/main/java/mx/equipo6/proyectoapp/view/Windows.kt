package mx.equipo6.proyectoapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Storefront
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
        var listaPantallas = listOf(HomeView, AboutUsView,
            ChatBotView, ShopView,
            ComunityView, CalenView,
            CartView, CheckoutView, ConfigView,
            TicketView, ForgotPasswordView)
        const val ROUTE_HOME = "Inicio"
        const val ROUTE_ABOUTUS = "Zazil"
        const val ROUTE_COMUNITY = "Comunidad"
        const val ROUTE_CONFIG = "Configuración"
        const val ROUTE_CHATBOT = "ChatBot"
        const val ROUTE_CALENDAR = "Calendario"
        const val ROUTE_STORE = "Tienda"
        const val ROUTE_CART = "Carrito"
        const val ROUTE_CHECKOUT = "Checkout"
        const val ROUTE_TICKET = "Ticket"
        const val ROUTE_FORGOT_PASSWORD = "ForgotPassword"
    }

    private data object HomeView : Windows(
        ROUTE_HOME,
        "Inicio",
        Icons.Default.Home
    )

    private data object AboutUsView : Windows(
        ROUTE_ABOUTUS,
        "Zazil",
        Icons.Default.Info
    )

    private data object ComunityView : Windows(
        ROUTE_COMUNITY,
        "Comunidad",
        Icons.Default.Person
    )

    private data object ConfigView : Windows(
        ROUTE_CONFIG,
        "Configuración",
        Icons.Default.Person
    )

    private data object ChatBotView : Windows(
        ROUTE_CHATBOT,
        "ChatBot",
        Icons.Default.Email
    )

    private data object CalenView : Windows(
        ROUTE_CALENDAR,
        "Calendario",
        Icons.Default.CalendarMonth
    )

    private data object ShopView : Windows(
        ROUTE_STORE,
        "Tienda",
        Icons.Default.ShoppingBag
    )

    private data object CartView : Windows(
        ROUTE_CART,
        "Carrito",
        Icons.Default.ShoppingCart
    )

    private data object CheckoutView : Windows(
        ROUTE_CHECKOUT,
        "Checkout",
        Icons.Default.CheckCircle
    )

    private data object TicketView : Windows(
        ROUTE_TICKET,
        "Ticket",
        Icons.Default.CheckCircle
    )

    private data object ForgotPasswordView : Windows(
        ROUTE_FORGOT_PASSWORD,
        "ForgotPassword",
        Icons.Default.ThumbUp
    )
}