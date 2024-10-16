package mx.equipo6.proyectoapp.view.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import mx.equipo6.proyectoapp.model.posts.Post
import mx.equipo6.proyectoapp.view.Windows
import mx.equipo6.proyectoapp.viewmodel.PostVM

/**
 * Composable que muestra un post en forma de tarjeta.
 * @author Ulises Jaaramillo Portilla | A01798380.
 * @param post Post a mostrar.
 */
@Composable
fun PostCard(post: Post, navController: NavHostController, postVM: PostVM, cardWidth: Dp = 450.dp) {
    val ctx = LocalContext.current
    var isFavorite by remember { mutableStateOf(post.favorite) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(cardWidth)
            .height(350.dp)
            .padding(start = 14.dp, end = 12.dp, top = 10.dp, bottom = 12.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate(Windows.ROUTE_COMUNITY + "/${post.post_id}")
                Toast.makeText(ctx, "Post: ${post.title}", Toast.LENGTH_SHORT).show()
            }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(Color(0xFFF5F5F5))
            ) {
                val painter: Painter = rememberAsyncImagePainter(model = post.image_path)
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = post.title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = post.date,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        text = "Por: ${post.partner_email}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = limitTextWords(post.summary),
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.LightGray,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(48.dp)
                    .padding(8.dp)
                    .clickable {
                        isFavorite = !isFavorite
                        post.favorite = isFavorite
                        postVM.onFavoriteButtonClicked(post.post_id, isFavorite)
                    }
            )
        }
    }
}

fun limitTextWords(text: String): String {
    val words = text.split(" ")
    return if (words.size > 20) {
        words.take(20).joinToString(" ") + "..."
    } else {
        text
    }
}
