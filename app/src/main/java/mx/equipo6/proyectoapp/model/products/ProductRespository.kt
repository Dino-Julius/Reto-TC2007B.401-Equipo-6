package mx.equipo6.proyectoapp.model.products

import mx.equipo6.proyectoapp.api.ApiService
import mx.equipo6.proyectoapp.model.posts.PostList
import javax.inject.Inject

/**
 * Clase que declara un repositorio de productos que extiende de [PostList]
 * @author Julio Vivas | A01749879
 * @param apiService Servicio de la API
 * @constructor Crea un repositorio de productos
 */
class ProductRespository @Inject constructor(private val apiService: ApiService) {
    /**
     * Función que obtiene los productos
     * @return Lista de productos
     */
    suspend fun getProducts(): ProductList {
        return apiService.getProducts()
    }
}