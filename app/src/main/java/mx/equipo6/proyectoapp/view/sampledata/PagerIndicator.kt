package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Composable que muestra un indicador de paginación con puntos.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param totalDots Número total de puntos a mostrar.
 * @param selectedIndex Índice del punto actualmente seleccionado.
 * @param modifier Modificador opcional para el contenedor del indicador.
 * @param activeColor Color del punto activo.
 * @param inactiveColor Color de los puntos inactivos.
 */
@Composable
fun PagerIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = Color.Gray
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(totalDots) { index ->
            val color = if (index == selectedIndex) activeColor else inactiveColor
            Canvas(
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 10.dp, start = 4.dp, end = 4.dp)
                    .size(8.dp)
            ) {
                drawCircle(color = color)
            }
        }
    }
}