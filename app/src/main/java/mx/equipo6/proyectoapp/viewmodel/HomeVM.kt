package mx.equipo6.proyectoapp.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.content.edit
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
import mx.equipo6.proyectoapp.model.posts.PostList
import mx.equipo6.proyectoapp.model.posts.PostRepository
import mx.equipo6.proyectoapp.network_di.NetworkChangeReceiver.NetworkChangeReceiver.isNetworkConnected
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val postRepository: PostRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _post = MutableStateFlow<ViewState<PostList>>(ViewState.Loading)
    val posts: StateFlow<ViewState<PostList>> get() = _post

    private val _isConnected = MutableStateFlow(isNetworkConnected(context))
    val isConnected: StateFlow<Boolean> get() = _isConnected

    private val networkChangeReceiver = object : BroadcastReceiver() {
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

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val results = postRepository.getPosts()
                _post.value = ViewState.Success(results)
            } catch (e: Exception) {
                _post.value = ViewState.Error("Failed to fetch posts")
            }
        }
    }

    fun saveSelectedButtons(context: Context, selectedButtons: List<ImageVector>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val buttonNames = selectedButtons.map { it.name }
                sharedPreferences.edit {
                    putStringSet("selected_buttons", buttonNames.toSet())
                }
            }
        }
    }

    fun loadSelectedButtons(context: Context, allUserButtons: List<ImageVector>, allShoppingButtons: List<ImageVector>): List<ImageVector> {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val buttonNames = sharedPreferences.getStringSet("selected_buttons", emptySet()) ?: emptySet()
        return buttonNames.mapNotNull { name ->
            allUserButtons.find { it.name == name } ?: allShoppingButtons.find { it.name == name }
        }
    }
}