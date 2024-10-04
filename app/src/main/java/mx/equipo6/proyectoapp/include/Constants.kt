package mx.equipo6.proyectoapp.include

// Define a class named Constants
/**
 * Define la clase con constantes
 * @author Julio Vivas
 * @property BASE_URL URL base de la API
 * @property LIST_OF_PRODUCTS Endpoint para la lista de productos
 */
class Constants {
// Objeto companion para mantener valores constantes
    companion object {
        // Base URL for the API
        const val BASE_URL = " https://fakestoreapi.com/"
        // Endpoint for the list of products
        const val LIST_OF_PRODUCTS = "products"
        // Endpoint for the list of posts
        const val LIST_OF_POSTS = "posts"
    }
}