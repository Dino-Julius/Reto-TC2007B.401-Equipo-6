package mx.equipo6.proyectoapp.model.products

import mx.equipo6.proyectoapp.api.ApiService
import javax.inject.Inject

class ProductRespository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts(): ProductList {
        return apiService.getProducts()
    }
}