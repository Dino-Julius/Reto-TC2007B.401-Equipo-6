package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

import androidx.compose.foundation.background

@Composable
fun CategoryItem(icon: ImageVector, title: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(40.dp)
        )
        Text(text = title, style = MaterialTheme.typography.bodySmall)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)

        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .width(41.dp) // Fixed width for the indicator line
                        .height(2.dp)
                        .background(color = Color.Black)
                        .align(Alignment.Center)
                )
            }
        }
    }
}