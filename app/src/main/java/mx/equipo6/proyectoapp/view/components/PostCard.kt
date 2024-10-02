//package mx.equipo6.proyectoapp.view.components
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import coil.compose.rememberImagePainter
//import mx.equipo6.proyectoapp.model.posts.Post
//
//@Composable
//fun PostCard(post: Post) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Column {
//            // Imagen del post
//            val painter: Painter = rememberImagePainter(data = post.image)
//            Image(
//                painter = painter,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//            )
//            // Content below the image
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = post.title,
//                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
//                        modifier = Modifier.weight(1f)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = post.author,
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.alignByBaseline()
//                    )
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = post.summary,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.shadow

@Composable
fun PostCard(
    imageResId: Int,
    title: String,
    author: String,
    summary: String,
    date: String
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp), ambientColor = Color.Gray, spotColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
        ) {
            // Imagen del post
            val painter: Painter = painterResource(id = imageResId)
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
                            text = title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Por: $author",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = summary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}