package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.*
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import mx.equipo6.proyectoapp.R

@Composable
fun CarouselCard(
    title: String,
    description: String,
) {
    val combinedText = if (title.isNotEmpty()) "$title\n\n$description" else description

    SampleCard(
        texto = combinedText,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(8.dp)
    )
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
            "Cuentan con:", "- Son elaboradas a mano con telas de algodón.\n" +
                    "- Son lavables y reutilizables.\n" +
                    "- Son fáciles de limpiar y secar.\n"
        ),
        Pair(
            "Cuentan con:", "- Cuentan con alta absorción.\n" +
                    "- Son hipoalergénicas.\n" +
                    "- Son impermeables.\n" +
                    "- Mantienen la piel fresca y libre de olores."
        ),
        Pair(
            "¿Qué esperar?", "Estamos comprometidas con el " +
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