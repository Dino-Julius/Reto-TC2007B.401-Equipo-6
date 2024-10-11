package mx.equipo6.proyectoapp.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.view.components.CategoryBar
import mx.equipo6.proyectoapp.view.components.PostCard
import mx.equipo6.proyectoapp.view.sampledata.FilterMenu
import mx.equipo6.proyectoapp.view.sampledata.SearchBar
import mx.equipo6.proyectoapp.view.sampledata.Title
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import mx.equipo6.proyectoapp.viewmodel.PostVM
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Composable que muestra la vista de la comunidad.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param postVM ViewModel de los posts.
 * @param navController Controlador de navegación.
 */
@Composable
fun CommunityView(postVM: PostVM, navController: NavHostController) {
    val postListViewState by postVM.posts.collectAsState()
    val isConnected by postVM.isConnected.collectAsState()
    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf("") }
    var delayedSearchQuery by remember { mutableStateOf("") }
    var searchTriggered by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Todo") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    var showFilterMenu by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.DESCENDING) }
    var showSearchBar by remember { mutableStateOf(true) }
    var noPostsInCategory by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val categoryBarState = rememberLazyListState()

    // Categorías específicas para ShopView
    val postCategories = listOf(
        "Todo" to Icons.Default.Home,
        "Salud" to Icons.Default.Favorite,
        "Inspirate" to Icons.Default.Lightbulb,
        "Bienestar" to Icons.Default.EmojiEmotions,
        "Eco" to Icons.Default.Forest
    )

    // Efecto lanzado cuando se realiza una búsqueda
    LaunchedEffect(searchQuery) {
        searchTriggered = false
        delay(1000)
        delayedSearchQuery = searchQuery
        searchTriggered = true
    }

    // Scroll CategoryBar a la maxima posición.
    LaunchedEffect(Unit) {
        categoryBarState.scrollToItem(0)
        listState.scrollToItem(0)
    }

    // Efecto lanzado cuando se actualiza el estado de la lista
    LaunchedEffect(listState) {
        var previousIndex = 0
        var previousScrollOffset = 0
        val scrollThresholdHide = 50
        val scrollThresholdShow = 100

        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, scrollOffset) ->
                if (index > previousIndex || (index == previousIndex && scrollOffset > previousScrollOffset + scrollThresholdHide)) {
                    showSearchBar = false
                } else if (index < previousIndex || scrollOffset < previousScrollOffset - scrollThresholdShow) {
                    showSearchBar = true
                }
                previousIndex = index
                previousScrollOffset = scrollOffset
            }
    }

    // Efecto lanzado cuando se carga la vista
    LaunchedEffect(Unit) {
        postVM.refreshPosts()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        ) {
            Title(
                "Comunidad",
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }

        // Barra de búsqueda.
        AnimatedVisibility(
            visible = showSearchBar,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                SearchBar(
                    modifier = Modifier.padding(bottom = 10.dp),
                    onValueChange = { query ->
                        searchQuery = query.text
                        if (query.text.isEmpty()) {
                            selectedCategory = "Todo"
                            selectedCategoryIndex = 0
                        } else {
                            val matchedCategory = (postListViewState as? ViewState.Success)?.data
                                ?.firstOrNull { post ->
                                    post.title.contains(
                                        query.text,
                                        ignoreCase = true
                                    ) && (selectedCategory == "Todo" || post.category.equals(selectedCategory, ignoreCase = true))
                                }
                                ?.category
                                ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                            if (matchedCategory != null && matchedCategory != selectedCategory) {
                                selectedCategory = matchedCategory
                                selectedCategoryIndex = when (matchedCategory) {
                                    "Todo" -> 0
                                    "Salud" -> 1
                                    "Inspirate" -> 2
                                    "Bienestar" -> 3
                                    "Eco" -> 4
                                    else -> 0
                                }
                            }
                        }
                    }
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-10).dp)
                        .align(Alignment.BottomCenter),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
                CategoryBar(
                    categories = postCategories,
                    selectedIndex = selectedCategoryIndex,
                    onCategorySelected = { category ->
                        selectedCategory = category
                        selectedCategoryIndex = when (category) {
                            "Todo" -> 0
                            "Salud" -> 1
                            "Inspirate" -> 2
                            "Bienestar" -> 3
                            "Eco" -> 4
                            else -> 0
                        }
                        postVM.refreshPosts()
                    },
                    state = categoryBarState)
            }

            Box {
                FilterButton(onClick = { showFilterMenu = true })
                FilterMenu(
                    showFilterMenu = showFilterMenu,
                    onDismissRequest = { showFilterMenu = false },
                    sortOrder = sortOrder,
                    onSortOrderChange = { newSortOrder -> sortOrder = newSortOrder },
                    menuText = "Ordenar por fecha"
                )
            }

            // SwipeRefresh para actualizar la lista de posts
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    swipeRefreshState.isRefreshing = true
                    postVM.refreshPosts()
                    swipeRefreshState.isRefreshing = false
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (isConnected) {
                        when (postListViewState) {
                            is ViewState.Success -> {
                                val postList = (postListViewState as ViewState.Success).data
                                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                val filteredPosts = postList.filter { post ->
                                    (selectedCategory == "Todo" || post.category.equals(
                                        selectedCategory,
                                        ignoreCase = true
                                    )) &&
                                            (post.title.contains(
                                                delayedSearchQuery,
                                                ignoreCase = true
                                            ) || post.partner_email.contains(
                                                delayedSearchQuery,
                                                ignoreCase = true
                                            ))
                                }.sortedWith(compareBy { post ->
                                    val date = dateFormat.parse(post.date)
                                    when (sortOrder) {
                                        SortOrder.ASCENDING -> date
                                        SortOrder.DESCENDING -> date?.let { -it.time }
                                    }
                                })
                                noPostsInCategory = postList.none { post -> post.category.equals(selectedCategory, ignoreCase = true) }
                                if (filteredPosts.isEmpty()) {
                                    if (delayedSearchQuery.isNotEmpty()) {
                                        ShowNoPostsMessage("No se encontraron resultados de la búsqueda")
                                    } else {
                                        ShowNoPostsMessage("No hay post en esta categoría")
                                    }
                                } else {
                                    LazyColumn(
                                        state = listState,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        itemsIndexed(filteredPosts) { _, post ->
                                            PostCard(post, navController)
                                        }
                                    }
                                }
                            }

                            is ViewState.Error -> {
                                val errorMsg = (postListViewState as ViewState.Error).message
                                ShowErrorMessage(errorMsg)
                            }

                            is ViewState.Loading -> {
                                LoadingScreen()
                            }
                        }
                    } else {
                        ShowErrorMessage("Not Connected to Internet ...")
                    }
                }
            }
        }
    }
}

/**
 * Enumeración que define el orden de clasificación.
 * @author Ulises Jaramillo Portilla | A01798380.
 */
enum class SortOrder {
    ASCENDING,
    DESCENDING
}

/**
 * Composable que muestra un botón de filtro.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param onClick Función lambda que se ejecuta cuando se hace clic en el botón.
 */
@Composable
fun FilterButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 22.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1D1F8)),
        ) {
            Text("Filtrar", color = Color.Black)
        }
    }
}

/**
 * Composable que muestra un mensaje cuando no hay posts.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param message Mensaje a mostrar.
 */
@Composable
private fun ShowNoPostsMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = message)
        }
    }
}

/**
 * Composable que muestra un mensaje de error.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param errorMsg Mensaje de error a mostrar.
 */
@Composable
private fun ShowErrorMessage(errorMsg: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_connection),
                contentDescription = "img error",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Text(text = errorMsg)
        }
    }
}

/**
 * Composable que muestra una pantalla de carga.
 * @author Ulises Jaramillo Portilla | A01798380.
 */
@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}