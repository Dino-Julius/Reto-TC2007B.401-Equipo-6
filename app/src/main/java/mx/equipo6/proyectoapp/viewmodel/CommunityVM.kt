//package mx.equipo6.proyectoapp.viewmodel
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.net.ConnectivityManager
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import dagger.hilt.android.qualifiers.ApplicationContext
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import mx.equipo6.proyectoapp.include.ViewState
//import mx.equipo6.proyectoapp.model.posts.Post
//import mx.equipo6.proyectoapp.model.posts.PostRepository
//import mx.equipo6.proyectoapp.network_di.NetworkChangeReceiver.NetworkChangeReceiver.isNetworkConnected
//import javax.inject.Inject
//
///**
// * ViewModel para manejar la vista de comunidad (posts)
// * @author Ulises Jaramillo Portilla | A01798380
// * @param postRepository Repositorio para manejar los datos de los posts
// * @param context Contexto de la aplicación para verificar el estado de la red
// */
//@HiltViewModel
//class CommunityVM @Inject constructor(
//    private val postRepository: PostRepository,
//    @ApplicationContext private val context: Context
//) : ViewModel() {
//
//    // Estado de los posts (Loading, Success, Error)
//    private val _postList = MutableStateFlow<ViewState<List<Post>>>(ViewState.Loading)
//    val postList: StateFlow<ViewState<List<Post>>> get() = _postList
//
//    // Estado de la red (conectado o desconectado)
//    private val _isConnected = MutableStateFlow(isNetworkConnected(context))
//    val isConnected: StateFlow<Boolean> get() = _isConnected
//
//    // BroadcastReceiver para monitorizar el estado de la red
//    private val networkChangeReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            _isConnected.value = isNetworkConnected(context = context!!)
//
//            if (_isConnected.value) {
//                fetchPosts()  // Si hay conexión, realiza la llamada API
//            } else {
//                _postList.value = ViewState.Error("Error de red, por favor revisa tu conexión")
//            }
//        }
//    }
//
//    init {
//        // Registrar el BroadcastReceiver
//        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        context.registerReceiver(networkChangeReceiver, intentFilter)
//        fetchPosts()  // Llama a la función para obtener los posts
//    }
//
//    private fun fetchPosts() {
//        viewModelScope.launch {
//            _postList.value = ViewState.Loading
//            try {
//                val results = postRepository.getPosts()  // Llama al repositorio para obtener los posts
//                _postList.value = ViewState.Success(results)
//                Log.d("TAG_SUCCESS", "fetchPosts: ")
//            } catch (e: Exception) {
//                Log.e("TAG_ERROR", "fetchPosts: ", e)
//                _postList.value = ViewState.Error("Ha ocurrido un error, intenta de nuevo")
//            }
//        }
//    }
//
//    // Llamado cuando el ViewModel se destruye
//    override fun onCleared() {
//        super.onCleared()
//        context.unregisterReceiver(networkChangeReceiver)  // Desregistrar el receptor
//    }
//
//    // Obtener un post por su ID
//    fun getPostById(postId: Int?): Post? {
//        return (postList.value as? ViewState.Success)?.data?.find { it.id == postId }
//    }
//}
