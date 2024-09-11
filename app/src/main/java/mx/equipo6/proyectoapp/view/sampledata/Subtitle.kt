package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Subtitulo de la pantalla
 * @author Equipo 6
 * @param texto Texto a mostrar
 * @param modifier Modificador de la vista
 */
@Composable
fun Subtitle(texto: String, modifier: Modifier = Modifier) {
    Text(text = texto,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp))
}