package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Composable que muestra un texto centrado con estilo de título
 * @author Equipo 6
 * @param texto Texto a mostrar
 * @param modifier Modificador de diseño
 */
@Composable
fun Title(
    texto: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = texto,
        textAlign = textAlign,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, bottom = 10.dp, top = 10.dp),
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 35.sp)
    )
}