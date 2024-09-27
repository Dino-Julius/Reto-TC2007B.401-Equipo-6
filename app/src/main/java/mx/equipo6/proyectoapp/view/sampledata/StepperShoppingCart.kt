package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * author: Jesus Guzman Ortega
 * Stepper: Shows a stepper with increment and decrement buttons.
 */
@Composable
fun Stepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int = 1,
    maxValue: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { if (value > minValue) onValueChange(value - 1) }
        ) {
            Icon(imageVector = Icons.Default.Remove , contentDescription = "Decrement")
        }
        Text(text = value.toString(), modifier = Modifier.padding(horizontal = 8.dp))
        IconButton(
            onClick = { if (value < maxValue) onValueChange(value + 1) }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Increment")
        }
    }
}