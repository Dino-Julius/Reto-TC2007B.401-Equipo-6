package mx.equipo6.proyectoapp.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
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
 * @author Ulises Jaramillo | A01798380.
 */
@HiltViewModel
class PostVM @Inject constructor(
    private val postRespository: PostRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("favorite_posts", Context.MODE_PRIVATE)

    private val _post = MutableStateFlow<ViewState<PostList>>(ViewState.Loading)
    val posts : StateFlow<ViewState<PostList>> get() = _post

    private val _isConnected = MutableStateFlow(isNetworkConnected(context))
    val isConnected : StateFlow<Boolean> get() = _isConnected

    private val networkChangeReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            _isConnected.value = isNetworkConnected(context = context!!)

            if (_isConnected.value) {
                fetchPosts()
            } else {
                _post.value = ViewState.Error("Network error..., Please check your internet connection")
            }
        }
    }

    init {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkChangeReceiver, intentFilter)
        fetchPosts()
    }

    fun onFavoriteButtonClicked(postId: Int, isFavorite: Boolean) {
        saveFavoritePost(postId, isFavorite)
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _post.value = ViewState.Loading
            try {
                val results = postRespository.getPosts()
                loadFavoritePosts(results)
                _post.value = ViewState.Success(results)
            } catch (e: Exception) {
                _post.value = ViewState.Error("An Error Occurred. Please try Again")
            }
        }
    }

    fun refreshPosts() {
        fetchPosts()
    }

    private fun loadFavoritePosts(posts: PostList) {
        val favoritePosts = sharedPreferences.getStringSet("favorites", emptySet()) ?: emptySet()
        posts.forEach { post ->
            post.favorite = favoritePosts.contains(post.post_id.toString())
        }
    }

    fun saveFavoritePost(postId: Int, isFavorite: Boolean) {
        val editor = sharedPreferences.edit()
        val favoritePosts = sharedPreferences.getStringSet("favorites", mutableSetOf()) ?: mutableSetOf()
        if (isFavorite) {
            favoritePosts.add(postId.toString())
        } else {
            favoritePosts.remove(postId.toString())
        }
        editor.putStringSet("favorites", favoritePosts)
        editor.apply()
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

    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(networkChangeReceiver)
    }

    fun getPostById(postId: Int?): Post? {
        return (posts.value as? ViewState.Success)?.data?.find { it.post_id == postId }
    }
}