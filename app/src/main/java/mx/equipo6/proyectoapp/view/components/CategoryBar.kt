package mx.equipo6.proyectoapp.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.view.sampledata.CategoryItem

/**
 * Composable que muestra una barra de categorías.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param selectedIndex Índice de la categoría seleccionada.
 * @param itemWidth Ancho de cada ítem.
 */
@Composable
fun CategoryBar(selectedIndex: Int, itemWidth: Float) {
    val listState = rememberLazyListState()
    var selectedCategoryIndex by remember { mutableStateOf(selectedIndex) }
    var lastScrollIndex by remember { mutableStateOf(0) }
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val itemWidthPx = with(density) { itemWidth.dp.toPx() }

    // Lista de categorías con efecto de desplazamiento.
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.collect { (firstVisibleItemIndex, offset) ->
            val currentScrollOffset = firstVisibleItemIndex * itemWidthPx + offset
            val newSelectedIndex = (currentScrollOffset / itemWidthPx).toInt()

            if (newSelectedIndex != lastScrollIndex) {
                selectedCategoryIndex = newSelectedIndex.coerceIn(0, categories.size - 1)
                lastScrollIndex = newSelectedIndex
            }
        }
    }

    // LazyRow con las categorías.
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

    // Efecto de desplazamiento al seleccionar una categoría.
    LaunchedEffect(selectedCategoryIndex) {
        coroutineScope.launch {
            delay(120) // Delay para esperar a que la lista se desplace.
            listState.animateScrollToItem(selectedCategoryIndex)
        }
    }
}