package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Subtitulo de la pantalla
 * @author Equipo 6
 * @param texto Texto a mostrar
 * @param modifier Modificador de la vista
 */
@Composable
fun Subtitle(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, bottom = 5.dp, top = 0.dp),
        fontWeight = FontWeight.Bold
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
    )
}