package mx.equipo6.proyectoapp.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.posts.Post
import mx.equipo6.proyectoapp.view.sampledata.Title
import mx.equipo6.proyectoapp.viewmodel.PostVM

/**
 * PostContentView: Muestra la vista de un post de la comunidad.
 * @autor Ulises Jaramillo Portilla | A01798380.
 * @param post Post a mostrar.
 * @param navController Controlador de navegación.
 * @param postVM ViewModel de los posts.
 */
@Composable
fun PostContentView(post: Post?, navController: NavHostController, postVM: PostVM) {
    // Estado para el contenido del archivo
    var fileContent by remember { mutableStateOf("Cargando...") }
    // Alcance de la corrutina
    val coroutineScope = rememberCoroutineScope()
    // Estado para el favorito
    var isFavorite by remember { mutableStateOf(post?.favorite ?: false) }

    // Efecto lanzado cuando cambia la ruta del archivo del post
    LaunchedEffect(post?.file_path) {
        post?.file_path?.let { path ->
            coroutineScope.launch {
                fileContent = postVM.readTextFromFile(path)
                Log.d("imagepost", fileContent)
            }
        }
    }

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

        // Columna con contenido desplazable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Título del post
            Title(
                post?.title ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            // Divisor horizontal
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            // Caja que contiene la imagen y el icono de favorito superpuestos
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                // Imagen del post
                Image(
                    painter = rememberAsyncImagePainter(model = post?.image_path),
                    contentDescription = "Post Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                // Icono de favorito
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(48.dp)
                        .padding(8.dp)
                        .clickable {
                            isFavorite = !isFavorite
                            post?.favorite = isFavorite
                        }
                )
            }

            // Divisor horizontal
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            // Caja con información del post
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Column {
                    // Fila con el autor y la fecha del post
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Por: ${post?.partner_email ?: "Desconocido"}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = post?.date ?: "Fecha desconocida",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Divisor horizontal
                    HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

                    // Resumen del post
                    Text(
                        text = post?.summary ?: "Sin resumen",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 16.dp)
                    )
                }
            }

            // Divisor horizontal
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            // Contenido del archivo del post
            Text(
                text = fileContent,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
            )
        }
    }
}