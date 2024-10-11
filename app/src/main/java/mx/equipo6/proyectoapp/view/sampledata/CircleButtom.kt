package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Composable que muestra un botón circular con un ícono.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param icon ImageVector del ícono a mostrar en el botón.
 * @param onClick Función lambda que se ejecuta cuando se hace clic en el botón.
 * @param onRemove Función lambda opcional que se ejecuta cuando se hace clic en el ícono de eliminar.
 */
@Composable
fun CircleButton(icon: ImageVector, onClick: () -> Unit, onRemove: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .size(66.dp)
            .then(if (onRemove == null) Modifier.shadow(8.dp, CircleShape) else Modifier)
            .background(color = Color(0xFFD1C4E9), shape = CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        if (onRemove != null) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(color = Color.Red, shape = CircleShape)
                    .align(Alignment.TopEnd)
                    .clickable(onClick = onRemove),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}