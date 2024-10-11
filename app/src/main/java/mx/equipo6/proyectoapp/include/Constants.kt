package mx.equipo6.proyectoapp.include

/**
 * Define la clase con constantes
 * @author Julio Vivas
 * @property API_URL URL base de la API
 * @property LIST_OF_PRODUCTS Endpoint para la lista de productos
 */
class Constants {
// Objeto companion para mantener valores constantes
    companion object {
        // Base URL for the API PRODUCTS
        const val API_URL = "http://104.248.55.22:3000/api/"
        // Endpoint for the list of products
        const val LIST_OF_PRODUCTS = "products"
        // Endpoint for the list of posts
        const val LIST_OF_POSTS = "posts"
        // Endpoint for chatbot
        const val CHATBOT_URL = "http://104.248.55.22:5000/"
    }
}