package mx.equipo6.proyectoapp.view

import PostCard
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mx.equipo6.proyectoapp.view.components.CategoryBar
import mx.equipo6.proyectoapp.view.sampledata.SearchBar
import mx.equipo6.proyectoapp.view.sampledata.Title
import androidx.compose.ui.Alignment
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.view.sampledata.Subtitle

@Composable
fun CommunityView(modifier: Modifier = Modifier) {
    val selectedIndex = 0 // Índice por defecto
    val itemWidth = 25f // Ancho de los ítems en píxeles

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp)
        ) {
            Title(
                "Comunidad",
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Start
            )

            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                ) {
                    SearchBar(modifier = Modifier.padding(bottom = 10.dp))

                    // Usamos Box para superponer el Divider sobre la CategoryBar
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Divider(
                            color = Color.LightGray,
                            thickness = 0.5.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = (-18).dp)
                                .align(Alignment.BottomCenter)
                        )
                        CategoryBar(
                            selectedIndex = selectedIndex,
                            itemWidth = itemWidth
                        )
                    }
                    PostCard(R.drawable.zazil, "Ejemplo 1", "Autor 1", "Texto de ejemplo de como sería la descripción del post.", "25/08/24")
                    PostCard(R.drawable.zazil, "Ejemplo 2", "Autor 2", "Texto de ejemplo de como sería la descripción del post.", "25/08/24")
                }
            }
        }
    }
}