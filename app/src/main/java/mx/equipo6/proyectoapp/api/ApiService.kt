package mx.equipo6.proyectoapp.api

import mx.equipo6.proyectoapp.model.auth.LoginRequest
import mx.equipo6.proyectoapp.model.auth.User
import mx.equipo6.proyectoapp.model.auth.LoginResponse
import mx.equipo6.proyectoapp.model.posts.PostList
import mx.equipo6.proyectoapp.model.products.ProductList
import mx.equipo6.proyectoapp.model.profileData.EditProfileRequest
import mx.equipo6.proyectoapp.model.profileData.EditProfileResponse
import mx.equipo6.proyectoapp.model.profileData.SignUpRequest
import mx.equipo6.proyectoapp.model.profileData.SignUpResponse
import mx.equipo6.proyectoapp.model.stripeAPI.OrderRequest
import mx.equipo6.proyectoapp.model.stripeAPI.PaymentIntentRequest
import mx.equipo6.proyectoapp.model.stripeAPI.PaymentIntentResponse
import mx.equipo6.proyectoapp.viewmodel.EmailRequest
import mx.equipo6.proyectoapp.viewmodel.ResetLinkResponse
import okhttp3.ResponseBody
import retrofit2.Call
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
    // Obtener todos los productos
    @GET("products")
    suspend fun getProducts(): ProductList

    // Obtener todos los posts
    @GET("posts")
    suspend fun getPosts(): PostList

    // Crear un nuevo usuario
    @POST("users")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    // Modificar un usuario
    @POST("users/{email}")
    suspend fun updateUser(@Path("email") email: String, @Body editProfileRequest: EditProfileRequest): EditProfileResponse

    // Obtener un usuario por email
    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    // Verificar la contraseña de un usuario
    @POST("user/login/{email}")
    suspend fun verifyPassword(@Path("email") email: String, @Body loginRequest: LoginRequest): LoginResponse

    // Enviar un enlace de restablecimiento de contraseña
    @POST("user/reset-password")
    suspend fun sendResetLink(@Body emailRequest: EmailRequest): ResetLinkResponse

    @POST("create-payment-intent")
    fun createPaymentIntent(@Body request: PaymentIntentRequest): Call<PaymentIntentResponse>

    // Enviar un pedido
    @POST("receive-order")
    suspend fun sendOrder(@Body orderRequest: OrderRequest): ResponseBody
}