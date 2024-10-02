package mx.equipo6.proyectoapp.api

import mx.equipo6.proyectoapp.include.Constants.Companion.LIST_OF_POSTS
import mx.equipo6.proyectoapp.include.Constants.Companion.LIST_OF_PRODUCTS
//import mx.equipo6.proyectoapp.model.posts.PostList
import mx.equipo6.proyectoapp.model.products.ProductList
import retrofit2.http.GET

/**
 * Define el servicio de la API para Retrofit
 * @author Julio Vivas
 * @property getProducts Define una petición GET para obtener la lista de productos
 */
interface ApiService {
    // Define a GET request to fetch the list of products
    @GET(LIST_OF_PRODUCTS)
    suspend fun getProducts(): ProductList

    //@GET(LIST_OF_POSTS)
    //suspend fun getPosts(): PostList
}