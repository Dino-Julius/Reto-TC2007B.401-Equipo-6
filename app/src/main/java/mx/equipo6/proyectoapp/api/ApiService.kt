package mx.equipo6.proyectoapp.api

import mx.equipo6.proyectoapp.include.Constants.Companion.LIST_OF_POSTS
import mx.equipo6.proyectoapp.include.Constants.Companion.LIST_OF_PRODUCTS
import mx.equipo6.proyectoapp.include.Constants.Companion.USERS_URL
import mx.equipo6.proyectoapp.model.auth.LoginRequest
import mx.equipo6.proyectoapp.model.auth.User
import mx.equipo6.proyectoapp.model.auth.LoginResponse
import mx.equipo6.proyectoapp.model.posts.PostList
import mx.equipo6.proyectoapp.model.products.ProductList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Define el servicio de la API para Retrofit
 * @author Julio Vivas
 * @property getProducts Define una petición GET para obtener la lista de productos
 */
interface ApiService {
    // Define a GET request to fetch the list of products
    @GET(LIST_OF_PRODUCTS)
    suspend fun getProducts(): ProductList

    @GET(LIST_OF_POSTS)
    suspend fun getPosts(): PostList

    @GET(USERS_URL)
    suspend fun getUsers(): List<User>

    @POST
    suspend fun authenticate(@Body LoginRequest: LoginRequest): LoginResponse

    @POST("signup")
    suspend fun signUp(@Body user: User): LoginResponse

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    @POST("users/login/{email}")
    suspend fun verifyPassword(@Path("email") email: String, @Body loginRequest: LoginRequest): LoginResponse
}