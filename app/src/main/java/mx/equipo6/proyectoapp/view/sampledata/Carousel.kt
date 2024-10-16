package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
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
            .height(180.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween, // Distribute the space evenly
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Only show title if it's not empty
            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .height(40.dp), // Fixed height for title
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(4.dp)) // Space between title and description
            Text(
                text = description,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontFamily = bellefair
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carousel() {
    val cards = listOf(
        Pair(
            "Nuestra toalla", "Experimenta la " +
                    "frescura, comodidad y " +
                    "protección que " +
                    "necesitas durante tu " +
                    "ciclo con nuestras " +
                    "toallas reutilizables."
        ),
        Pair(
            "Cuentan con: ", "- Son elaboradas a mano con telas de algodón.\n" +
                    "- Son lavables y reutilizables.\n" +
                    "- Son fáciles de limpiar y secar.\n"
        ),
        Pair(
            "Cuentan con: ", "- Cuentan con alta absorción.\n" +
                    "- Son hipoalergénicas.\n" +
                    "- Son impermeables.\n" +
                    "- Mantienen la piel fresca y libre de olores."
        ),
        Pair(
            "", "Estamos comprometidas con el " +
                    "bienestar de las mujeres y el cuidado " +
                    "del planeta.\n" +
                    "Creamos productos de alta " +
                    "calidad y amigables con el ambiente, " +
                    "ayudando a mujeres."
        )
    )

    val pagerState = rememberPagerState()

    HorizontalPager(
        count = cards.size,
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        CarouselCard(
            title = cards[page].first,
            description = cards[page].second,
            backgroundColor = Color(0xFFF4D0CB) // card color
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        PagerIndicator(
            totalDots = cards.size,
            selectedIndex = pagerState.currentPage,
            modifier = Modifier.padding(4.dp)
        )
    }
}