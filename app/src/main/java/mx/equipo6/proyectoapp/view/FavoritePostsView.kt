package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.view.components.PostCard
import mx.equipo6.proyectoapp.view.sampledata.Subtitle
import mx.equipo6.proyectoapp.view.sampledata.Title
import mx.equipo6.proyectoapp.viewmodel.PostVM

@Composable
fun FavoritePostsView(postVM: PostVM, navController: NavHostController) {
    val postListViewState by postVM.posts.collectAsState()
    val favoritePosts =
        (postListViewState as? ViewState.Success)?.data?.filter { it.favorite } ?: emptyList()

    // Columna principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Fila para el botón de regresar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color(0x8DE7E1E1), shape = CircleShape)
                    .clip(CircleShape)
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Regresar",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 7.dp)
            )
        }

        // Divisor horizontal
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

        Column(modifier = Modifier.fillMaxSize()) {
            Subtitle(
                "Mis posts favoritos",
                modifier = Modifier.padding(16.dp),
            )
            if (favoritePosts.isEmpty()) {
                ShowNoPostsMessage("No hay posts favoritos")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(favoritePosts) { _, post ->
                        PostCard(
                            post = post,
                            navController = navController,
                            postVM = postVM, // Pass the ViewModel here
                            cardWidth = 445.dp
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable que muestra un mensaje cuando no hay posts.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param message Mensaje a mostrar.
 */
@Composable
private fun ShowNoPostsMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = message)
        }
    }
}