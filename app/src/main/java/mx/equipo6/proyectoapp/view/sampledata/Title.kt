package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * Composable que muestra un texto centrado con estilo de título
 * @author Equipo 6
 * @param texto Texto a mostrar
 * @param modifier Modificador de diseño
 */
@Composable
fun Title(texto: String, modifier: Modifier = Modifier) {
    Text(text = texto,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.fillMaxWidth())
}