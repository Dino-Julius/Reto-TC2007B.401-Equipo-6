package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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

@Composable
fun CarouselCard(
    title: String,
    description: String,
    backgroundColor: Color = Color(0xFFFFFFFF)
) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp) // Adjusted card height
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalArrangement = Arrangement.Top, // Align contents at the top
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title with fixed height to ensure alignment
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.height(40.dp), // Fixed height for title
                textAlign = TextAlign.Center
            )
            Text(
                text = description,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontFamily = bellefair
            )
        }
    }
}

@Composable
fun Carousel() {
    val cards = listOf(
        Pair("Nuestra toalla", "Experimenta la " +
                "frescura, comodidad y " +
                "protección que " +
                "necesitas durante tu " +
                "ciclo con nuestras " +
                "toallas reutilizables."),
        Pair("Cuentan con: ", "- Son elaboradas a mano con telas de algodón.\n" +
                "- Son lavables y reutilizables.\n" +
                "- Son fáciles de limpiar y secar.\n"),
        Pair("Cuentan con: ", "- Cuentan con alta absorción.\n" +
                "- Son hipoalergénicas.\n" +
                "- Son impermeables.\n" +
                "- Mantienen la piel fresca y libre de olores."),
        Pair("Producto 4", "Descripción del producto 4")
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(cards) { card ->
            CarouselCard(
                title = card.first,
                description = card.second,
                backgroundColor = Color(0xFFF4D0CB) // card color
            )
        }
    }
}
