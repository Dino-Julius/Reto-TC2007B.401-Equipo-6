package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mx.equipo6.proyectoapp.view.SortOrder

/**
 * Composable que muestra un menú desplegable para filtrar los elementos.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param showFilterMenu Booleano que indica si el menú de filtro está visible.
 * @param onDismissRequest Función lambda que se ejecuta cuando se solicita cerrar el menú.
 * @param sortOrder Orden de clasificación actual.
 * @param onSortOrderChange Función lambda que se ejecuta cuando cambia el orden de clasificación.
 * @param menuText Texto a mostrar en el menú.
 */
@Composable
fun FilterMenu(
    showFilterMenu: Boolean,
    onDismissRequest: () -> Unit,
    sortOrder: SortOrder,
    onSortOrderChange: (SortOrder) -> Unit,
    menuText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 22.dp)
            .offset(x = 160.dp, y = 50.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        DropdownMenu(
            expanded = showFilterMenu,
            onDismissRequest = onDismissRequest,
            modifier = Modifier
                .background(Color(0xFFF6ECF9))
        ) {
            DropdownMenuItem(onClick = {
                onSortOrderChange(SortOrder.ASCENDING)
                onDismissRequest()
            }, text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(menuText + " ascendente")
                    if (sortOrder == SortOrder.ASCENDING) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.Green
                        )
                    }
                }
            })
            DropdownMenuItem(onClick = {
                onSortOrderChange(SortOrder.DESCENDING)
                onDismissRequest()
            }, text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(menuText + " descendente")
                    if (sortOrder == SortOrder.DESCENDING) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.Green
                        )
                    }
                }
            })
        }
    }
}