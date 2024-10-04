package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import mx.equipo6.proyectoapp.view.components.categories

@Composable
fun CategoryBar(selectedIndex: Int, itemWidth: Float) {
    val listState = rememberLazyListState()
    var selectedCategoryIndex by remember { mutableStateOf(selectedIndex) } // Usa el índice seleccionado pasado
    var lastScrollIndex by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    // Determina el ancho de cada ítem (ajusta este valor al ancho real de tus ítems)
    val itemWidthPx = with(density) { itemWidth.dp.toPx() } // Usa el ancho pasado en lugar de un valor fijo

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.collect { (firstVisibleItemIndex, offset) ->
            // Calcula la posición de desplazamiento actual en términos de ítems completos
            val currentScrollOffset = firstVisibleItemIndex * itemWidthPx + offset

            // Calcula el nuevo índice de selección basado en el desplazamiento total
            val newSelectedIndex = (currentScrollOffset / itemWidthPx).toInt()

            // Evitar actualizaciones si el índice seleccionado no ha cambiado
            if (newSelectedIndex != lastScrollIndex) {
                // Actualiza el índice seleccionado
                selectedCategoryIndex = newSelectedIndex.coerceIn(0, categories.size - 1)
                lastScrollIndex = newSelectedIndex
            }
        }
    }

    Box {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(categories) { category ->
                val isSelected = categories.indexOf(category) == selectedCategoryIndex
                CategoryItem(
                    icon = category.second,
                    title = category.first,
                    isSelected = isSelected,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )
            }
        }
    }
}
