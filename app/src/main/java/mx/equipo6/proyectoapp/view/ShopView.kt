package mx.equipo6.proyectoapp.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.model.products.Products
import mx.equipo6.proyectoapp.view.components.CategoryBar
import mx.equipo6.proyectoapp.view.sampledata.FilterMenu
import mx.equipo6.proyectoapp.view.sampledata.ProductQuantityDialog
import mx.equipo6.proyectoapp.view.sampledata.SearchBar
import mx.equipo6.proyectoapp.view.sampledata.Title
import mx.equipo6.proyectoapp.viewmodel.ProductVM
import java.util.Locale

/**
 * ShopView: Vista principal de la tienda, muestra los productos en una vista de cuadrícula.
 * @author Julio Vivas; Ulises Jaramillo Portilla | A01798380.
 * @param productVM ViewModel de los productos.
 * @param navController Controlador de navegación.
 */
@Composable
fun ShopView(productVM: ProductVM, navController: NavHostController) {
    val productListViewState by productVM.products.collectAsState()
    val isConnected by productVM.isConnected.collectAsState()
    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf("") }
    var delayedSearchQuery by remember { mutableStateOf("") }
    var searchTriggered by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Todo") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    var showFilterMenu by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.DESCENDING) }
    var showSearchBar by remember { mutableStateOf(true) }
    var noProductsInCategory by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val categoryBarState = rememberLazyListState()

    // Categorías específicas para ShopView
    val shopCategories = listOf(
        "Todo" to Icons.Default.Home,
        "Regulares" to Icons.Default.Favorite,
        "Nocturnas" to Icons.Default.Lightbulb,
        "Teen" to Icons.Default.EmojiEmotions,
        "Pantiprotectores" to Icons.Default.Forest
    )

    LaunchedEffect(searchQuery) {
        searchTriggered = false
        delay(1000)
        delayedSearchQuery = searchQuery
        searchTriggered = true
    }

    LaunchedEffect(Unit) {
        categoryBarState.scrollToItem(0)
        listState.scrollToItem(0)
    }

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

    LaunchedEffect(Unit) {
        productVM.refreshProducts()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        ) {
            Title(
                "Tienda",
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }

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
                            val matchedCategory = (productListViewState as? ViewState.Success)?.data
                                ?.firstOrNull { post ->
                                    post.name.contains(
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
                                    "Regulares" -> 1
                                    "Nocturnas" -> 2
                                    "Teen" -> 3
                                    "Pantiprotectores" -> 4
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
                    categories = shopCategories,
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
                        productVM.refreshProducts()
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
                    menuText = "Ordenar por precio"
                )
            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    swipeRefreshState.isRefreshing = true
                    productVM.refreshProducts()
                    swipeRefreshState.isRefreshing = false
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (isConnected) {
                        when (productListViewState) {
                            is ViewState.Success -> {
                                val productList = (productListViewState as ViewState.Success).data
                                val filteredProducts = productList.filter { product ->
                                    (selectedCategory == "Todo" || product.category.equals(
                                        selectedCategory,
                                        ignoreCase = true
                                    )) &&
                                            (product.name.contains(
                                                delayedSearchQuery,
                                                ignoreCase = true
                                            ))
                                }.sortedWith(compareBy { product ->
                                    val price = product.price
                                    when (sortOrder) {
                                        SortOrder.ASCENDING -> price
                                        SortOrder.DESCENDING -> -price
                                    }
                                })
                                noProductsInCategory = productList.none { product ->
                                    product.category.equals(
                                        selectedCategory,
                                        ignoreCase = true
                                    )
                                }
                                if (filteredProducts.isEmpty()) {
                                    if (delayedSearchQuery.isNotEmpty()) {
                                        ShowNoProductMessage("No se encontraron resultados de la búsqueda")
                                    } else {
                                        ShowNoProductMessage("No hay productos en esta categoría")
                                    }
                                } else {
                                    LazyColumn(
                                        state = listState,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        items(filteredProducts.chunked(2)) { rowItems ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 10.dp, end = 10.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                for (product in rowItems) {
                                                    ProductsCardUI(
                                                        product,
                                                        navController,
                                                        productVM
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            is ViewState.Error -> {
                                val errorMsg = (productListViewState as ViewState.Error).message
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
 * Composable que muestra un mensaje cuando no hay productos.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param message Mensaje a mostrar.
 */
@Composable
private fun ShowNoProductMessage(message: String) {
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
 * @author Julio Vivas.
 * @param errorMsg Mensaje de error a mostrar.
 */
@Composable
private fun ShowErrorMessage(errorMsg: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_connection),
                contentDescription ="img error",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp))
            Text(text = errorMsg)
        }
    }
}

/**
 * Composable que muestra una pantalla de carga.
 * @author Julio Vivas.
 */
@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.background
        )
    }
}

/**
 * Composable que muestra la tarjeta de un producto.
 * @author Julio Vivas.
 * @param products Producto a mostrar.
 * @param navController Controlador de navegación.
 * @param productVM ViewModel de los productos.
 */
@Composable
fun ProductsCardUI(products: Products, navController: NavHostController, productVM: ProductVM) {
    val ctx = LocalContext.current
    val quantityToAdd = remember { mutableIntStateOf(1) }
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .height(270.dp)
            .width(212.dp)
            .padding(6.dp)
            .clickable {
                navController.navigate(Windows.ROUTE_STORE + "/${products.sku}")
                Toast.makeText(ctx, "Product: ${products.name}", Toast.LENGTH_SHORT).show()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp), // Half of the card height
                painter = rememberAsyncImagePainter(model = products.image_path),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp)
            ) {
                Text(
                    text = products.name,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Text(
                    text = products.description,
                    modifier = Modifier.padding(top = 3.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${products.price}",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                showDialog.value = true // Trigger dialog
                            }
                            .clip(shape = CircleShape)
                            .background(Color(0xFFC7A8BC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    // Call the dialog function and pass necessary parameters
    ProductQuantityDialog(
        showDialog = showDialog,
        product = products,
        quantityToAdd = quantityToAdd,
        productVM = productVM,
        ctx = ctx
    )
}