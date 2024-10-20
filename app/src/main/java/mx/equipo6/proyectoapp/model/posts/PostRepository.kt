package mx.equipo6.proyectoapp.model.posts

import mx.equipo6.proyectoapp.api.ApiService
import javax.inject.Inject

/**
 * Clase que declara un repositorio de productos que extiende de [PostList]
 * @author Ulises Jaramillo Portilla | A01798380
 * @param apiService Servicio de la API
 * @constructor Crea un repositorio de productos
 */
class PostRepository @Inject constructor(private val apiService: ApiService) {
    /**
     * Función que obtiene la lista de productos
     * @return Lista de productos
     */
    suspend fun getPosts(): PostList {
        return apiService.getPosts()
    }
}