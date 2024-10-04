package mx.equipo6.proyectoapp.view

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import mx.equipo6.proyectoapp.model.posts.Post
import mx.equipo6.proyectoapp.view.sampledata.Stepper
import mx.equipo6.proyectoapp.view.sampledata.Title
import java.io.File
import java.io.IOException
import mx.equipo6.proyectoapp.viewmodel.PostVM
import mx.equipo6.proyectoapp.viewmodel.ProductVM

/**
 * PostContentView: Muestra la vista de un post de la comunidad.
 * @autor Ulises Jaramillo Portilla | A01798380.
 * @param post Post a mostrar.
 * @param navController navigation controller
 * @param postVM ViewModel.
 */
@Composable
fun PostContentView(post: Post?, navController: NavHostController, postVM: PostVM) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
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

        Divider(color = Color.LightGray, thickness = 0.5.dp)

        Title(
            post?.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )

        Image(
            painter = rememberAsyncImagePainter(model = post?.image_path),
            contentDescription = "Post Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(start = 20.dp, end = 20.dp)
        )

        Divider(color = Color.LightGray, thickness = 0.5.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Por: ${post?.partner_id}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post?.date ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    text = post?.summary ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp)
                )
            }
        }

        Divider(color = Color.LightGray, thickness = 0.5.dp)

        // Mostrar el contenido del archivo
        val fileContent = post?.file_path?.let { postVM.readTextFromFile(it) } ?: "No file path available"

        Text(
            text = fileContent,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}