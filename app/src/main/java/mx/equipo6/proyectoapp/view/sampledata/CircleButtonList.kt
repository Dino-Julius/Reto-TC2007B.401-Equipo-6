package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

/**
 * Composable que muestra una lista de botones circulares organizados en categorías.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param userButtons Lista de pares de íconos y nombres para los botones de usuario.
 * @param shoppingButtons Lista de pares de íconos y nombres para los botones de compras.
 * @param onButtonSelected Función lambda que se ejecuta cuando se selecciona un botón.
 */
@Composable
fun CircleButtonList(
    userButtons: List<Pair<ImageVector, String>>,
    shoppingButtons: List<Pair<ImageVector, String>>,
    onButtonSelected: (ImageVector) -> Unit
) {
    val buttonWidth = 0.3f // Ancho de los botones alineados por fila.

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color(0xFFFFE4E1), shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la sección de usuario
        Text("Comunidad", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        if (userButtons.isEmpty()) {
            // Texto a mostrar si no hay botones de usuario
            Text("Accesos en uso", style = MaterialTheme.typography.bodyLarge)
        } else {
            // Fila de botones de usuario
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                userButtons.forEach { (icon, name) ->
                    Column(
                        modifier = Modifier.weight(buttonWidth),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircleButton(icon = icon, onClick = { onButtonSelected(icon)})
                        // Texto debajo del botón
                        Text(text = name, modifier = Modifier.padding(top = 12.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        // Título de la sección de compras
        Text("Compras", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        if (shoppingButtons.isEmpty()) {
            // Texto a mostrar si no hay botones de compras
            Text("Accesos en uso", style = MaterialTheme.typography.bodyLarge)
        } else {
            // Fila de botones de compras
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                shoppingButtons.forEach { (icon, name) ->
                    Column(
                        modifier = Modifier.weight(buttonWidth),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircleButton(icon = icon, onClick = { onButtonSelected(icon) })
                        // Texto debajo del botón
                        Text(text = name, modifier = Modifier.padding(top = 12.dp))
                    }
                }
            }
        }
    }
}