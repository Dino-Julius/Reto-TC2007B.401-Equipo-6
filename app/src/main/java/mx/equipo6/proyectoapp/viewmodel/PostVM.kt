package mx.equipo6.proyectoapp.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.model.posts.Post
import mx.equipo6.proyectoapp.model.posts.PostList
import mx.equipo6.proyectoapp.model.posts.PostRepository
import mx.equipo6.proyectoapp.network_di.NetworkChangeReceiver.NetworkChangeReceiver.isNetworkConnected
import java.io.File
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel para mostrar la lista de posts de la comunidad.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param postRespository ProductRespository
 * @param context Context
 */
@HiltViewModel
class PostVM @Inject constructor(
    private val postRespository: PostRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    // Se inicializa el estado de la lista de posts.
    private val _post = MutableStateFlow<ViewState<PostList>>(ViewState.Loading)
    val posts : StateFlow<ViewState<PostList>> get() = _post

    // Se revisa si hay conexión a internet.
    private val _isConnected = MutableStateFlow(isNetworkConnected(context))
    val isConnected : StateFlow<Boolean> get() = _isConnected

    // Registra el receptor de transmisión.
    private val networkChangeReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            _isConnected.value = isNetworkConnected(context = context!!)

            if (_isConnected.value) {
                // Si hay conexión, se realiza la llamada a la API.
                fetchPosts()
            } else {
                // Si no hay conexión, se restablece la llamada a la API.
                _post.value = ViewState.Error("Network error..., Please check your internet connection")
            }
        }
    }

    init {
        // Registra el receptor en el contexto.
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkChangeReceiver, intentFilter)
        fetchPosts()
    }

    // Se obtienen los posts.
    private fun fetchPosts() {
        viewModelScope.launch {
            _post.value = ViewState.Loading
            try {
                val results = postRespository.getPosts()
                _post.value = ViewState.Success(results)
                Log.e("TAG_SUCCESS", "fetchProducts: ")
            } catch (e: Exception) {
                Log.e("TAG_ERROR", "fetchProducts: ")
                _post.value = ViewState.Error("An Error Occurred. Please try Again")
            }
        }
    }

    // Función para leer texto desde un archivo dado el file path
    private suspend fun fetchFileContentFromUrl(url: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string() ?: "Error al leer el archivo: respuesta vacía"
                } else {
                    "Error al leer el archivo: ${response.message}"
                }
            } catch (e: IOException) {
                e.printStackTrace()
                "Error al leer el archivo: ${e.message}"
            }
        }
    }

    suspend fun readTextFromFile(filePath: String): String {
        return if (filePath.startsWith("http")) {
            fetchFileContentFromUrl(filePath)
        } else {
            try {
                File(filePath).readText()
            } catch (e: IOException) {
                e.printStackTrace()
                "Error al leer el archivo: ${e.message}"
            }
        }
    }


    fun refreshPosts() {
        fetchPosts()
    }

    // Se libera el receptor.
    override fun onCleared() {
        super.onCleared()
        // unRegister
        context.unregisterReceiver(networkChangeReceiver)
    }

    // Se obtiene un post por su ID.
    fun getPostById(postId: Int?): Post? {
        return (posts.value as? ViewState.Success)?.data?.find { it.post_id == postId }
    }
}