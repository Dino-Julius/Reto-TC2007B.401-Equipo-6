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

@Composable
fun CircleButtonList(
    userButtons: List<Pair<ImageVector, String>>,
    shoppingButtons: List<Pair<ImageVector, String>>,
    onButtonSelected: (ImageVector) -> Unit
) {
    val buttonWidth = 0.3f // botones alineados por fila.

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color(0xFFFFE4E1), shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Usuario", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        if (userButtons.isEmpty()) {
            Text("Accesos en uso", style = MaterialTheme.typography.bodyLarge)
        } else {
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
                        CircleButton(icon = icon, onClick = { onButtonSelected(icon) })
                        Text(text = name, modifier = Modifier.padding(top = 12.dp)) // Separación entre el botón y el texto.
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text("Compras", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        if (shoppingButtons.isEmpty()) {
            Text("Accesos en uso", style = MaterialTheme.typography.bodyLarge)
        } else {
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
                        Text(text = name, modifier = Modifier.padding(top = 12.dp)) // Separación entre el botón y el texto.
                    }
                }
            }
        }
    }
}