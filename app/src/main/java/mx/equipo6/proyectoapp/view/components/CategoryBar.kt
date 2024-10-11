package mx.equipo6.proyectoapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.view.sampledata.CategoryItem

/**
 * Composable que muestra una barra de categorías.
 * @param categories Lista de categorías a mostrar.
 * @param selectedIndex Índice de la categoría seleccionada.
 */
@Composable
fun CategoryBar(
    categories: List<Pair<String, ImageVector>>,
    selectedIndex: Int,
    onCategorySelected: (String) -> Unit,
    state: LazyListState
    ) {
    var selectedCategoryIndex by remember { mutableIntStateOf(selectedIndex) }
    val coroutineScope = rememberCoroutineScope()


    // LazyRow con las categorías.
    Box {
        LazyRow(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(categories) { (category, icon) ->
                val index = categories.indexOfFirst { it.first == category }
                val isSelected = index == selectedCategoryIndex
                CategoryItem(
                    icon = icon,
                    title = category,
                    isSelected = isSelected,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .clickable {
                            selectedCategoryIndex = index
                            onCategorySelected(category)
                            if (index == 3) {
                                coroutineScope.launch {
                                    state.animateScrollToItem(index + 1)
                                }
                            }
                            if (index == 1) {
                                coroutineScope.launch {
                                    state.animateScrollToItem(0)
                                }
                            }
                        }
                )
            }
        }
    }
}