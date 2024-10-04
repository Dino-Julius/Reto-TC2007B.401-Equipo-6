package mx.equipo6.proyectoapp.view.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import mx.equipo6.proyectoapp.model.posts.Post
import mx.equipo6.proyectoapp.view.Windows
import mx.equipo6.proyectoapp.viewmodel.PostVM

/**
 * Composable que muestra un post en forma de tarjeta.
 * @author Ulises Jaaramillo Portilla | A01798380.
 * @param post Post a mostrar.
 */
@Composable
fun PostCard(post: Post, navController: NavHostController, postVM: PostVM) {
    val ctx = LocalContext.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp), ambientColor = Color.Gray, spotColor = Color.Black)
            .clickable {
                navController.navigate(Windows.ROUTE_COMUNITY + "/${post.post_id}")
                Toast.makeText(ctx, "Post: ${post.title}", Toast.LENGTH_SHORT).show()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
        ) {
            // Imagen del post
            val painter: Painter = rememberImagePainter(data = "http://104.248.55.22/" + post.image_path)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            // Contenido debajo de la imagen.
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = post.title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Por: ${post.partner_id}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = post.date,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.summary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}