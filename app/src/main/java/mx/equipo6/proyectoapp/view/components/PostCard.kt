package mx.equipo6.proyectoapp.view.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import mx.equipo6.proyectoapp.model.posts.Post
import mx.equipo6.proyectoapp.view.Windows

/**
 * Composable que muestra un post en forma de tarjeta.
 * @author Ulises Jaaramillo Portilla | A01798380.
 * @param post Post a mostrar.
 */
@Composable
fun PostCard(post: Post, navController: NavHostController) {
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
            val painter: Painter = rememberAsyncImagePainter(model = post.image_path)
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
                    .fillMaxSize()
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
                            text = "Por: ${post.partner_email}",
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
                    text = limitTextWords(post.summary),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

fun limitTextWords(text: String): String {
    val words = text.split(" ")
    return if (words.size > 30) {
        words.take(30).joinToString(" ") + "..."
    } else {
        text
    }
}