package mx.equipo6.proyectoapp.api

import mx.equipo6.proyectoapp.include.Constants.Companion.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Clase que define el cliente de Retrofit para la API
 * @property apiService Define el servicio de la API
 * @author Julio Vivas | A01749879
 */
object RetrofitClient {
    // URL base de la API
    private const val BASE_URL = API_URL

    // Servicio de la API
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}