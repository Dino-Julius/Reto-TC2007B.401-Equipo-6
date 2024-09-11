package mx.equipo6.proyectoapp.api

// Import necessary constants and models
import mx.equipo6.proyectoapp.include.Constants.Companion.List_Of_product
import mx.equipo6.proyectoapp.model.products.ProductList
import retrofit2.http.GET

// Define the ApiService interface for Retrofit
interface ApiService {
    // Define a GET request to fetch the list of products
    @GET(List_Of_product)
    suspend fun getProducts(): ProductList
}