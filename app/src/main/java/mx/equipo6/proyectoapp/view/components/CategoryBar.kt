package mx.equipo6.proyectoapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.view.sampledata.CategoryItem

/**
 * Composable que muestra una barra de categorías.
 * @param selectedIndex Índice de la categoría seleccionada.
 */
@Composable
fun CategoryBar(selectedIndex: Int) {
    val listState = rememberLazyListState()
    var selectedCategoryIndex by remember { mutableIntStateOf(selectedIndex) }
    val coroutineScope = rememberCoroutineScope()

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
                val index = categories.indexOf(category)
                val isSelected = index == selectedCategoryIndex
                CategoryItem(
                    icon = category.second,
                    title = category.first,
                    isSelected = isSelected,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .clickable {
                            selectedCategoryIndex = index
                            if (index == 3) {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index + 1)
                                }
                            }
                            if (index == 1) {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            }
                        }
                )
            }
        }
    }
}