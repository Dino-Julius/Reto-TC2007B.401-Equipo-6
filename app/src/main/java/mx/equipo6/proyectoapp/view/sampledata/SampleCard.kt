package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.equipo6.proyectoapp.R

val bellefair = FontFamily(Font(R.font.bellefair_regular))

/**
 * Carta de la pantalla
 * @param texto Texto a mostrar
 * @param modifier Modificador de la vista
 */
@Composable
fun SampleCard(texto: String, modifier: Modifier = Modifier) {
    val lines = texto.split("\n\n")
    val title = lines.getOrNull(0) ?: ""
    val description = lines.getOrNull(1) ?: ""

    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(8.dp), // Reduced elevation
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF4D0CB),
            contentColor = Color.Black // Set text color to black
        ),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold, // Set title to bold
                    color = Color.Black,
                    fontFamily = bellefair, // Set font family to bellefair
                    fontSize = 25.sp, // Increase font size
                    modifier = Modifier.padding(bottom = 4.dp) // Adjust padding between title and description
                )
            }
            Text(
                text = description,
                color = Color.Black,
                fontFamily = bellefair, // Set font family to bellefair
                fontSize = 20.sp, // Increase font size
                textAlign = TextAlign.Justify // Justify the description text
            )
        }
    }
}