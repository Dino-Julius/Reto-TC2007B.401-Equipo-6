package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.view.components.PostCard
import mx.equipo6.proyectoapp.view.sampledata.Carousel
import mx.equipo6.proyectoapp.view.sampledata.CircleButton
import mx.equipo6.proyectoapp.view.sampledata.CircleButtonList
import mx.equipo6.proyectoapp.view.sampledata.PagerIndicator
import mx.equipo6.proyectoapp.view.sampledata.RectangularButton
import mx.equipo6.proyectoapp.view.sampledata.Subtitle
import mx.equipo6.proyectoapp.viewmodel.HomeVM
import mx.equipo6.proyectoapp.viewmodel.SignUpViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeView(homeVM: HomeVM = viewModel(), navController: NavHostController, signUpViewModel: SignUpViewModel) {
    val context = LocalContext.current
    val isConnected by homeVM.isConnected.collectAsState()
    val postListViewState by homeVM.posts.collectAsState()
    val pagerState = rememberPagerState()
    val allUserButtons = listOf(
        Icons.Default.Home to "Inicio",
        Icons.Default.Settings to "Configuración",
        Icons.Default.Favorite to "Favoritos"
    )
    val allShoppingButtons = listOf(
        Icons.Default.Share to "Compartir",
        Icons.Default.Search to "Buscar",
        Icons.Default.Notifications to "Notificaciones"
    )
    val selectedButtons = remember { mutableStateListOf(*homeVM.loadSelectedButtons(context, allUserButtons.map { it.first }, allShoppingButtons.map { it.first }).toTypedArray()) }
    val availableUserButtons = remember { mutableStateListOf(*allUserButtons.toTypedArray()) }
    val availableShoppingButtons = remember { mutableStateListOf(*allShoppingButtons.toTypedArray()) }
    var showButtonList by remember { mutableStateOf(false) }

    val postsByCategory = (postListViewState as? ViewState.Success)?.data?.groupBy { it.category } ?: emptyMap()
    val selectedPosts = postsByCategory.flatMap { (_, posts) ->
        posts.sortedByDescending { it.date }.take(5)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp)
        ) {
            Subtitle(
                "¡Hola, ${signUpViewModel.firstName.value}!",
                modifier = Modifier.padding(bottom = 10.dp)
            )
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))

            Spacer(
                modifier = if (selectedButtons.isEmpty()) Modifier.height(1.dp) else Modifier.height(
                    4.dp
                )
            )

            if (selectedButtons.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    selectedButtons.chunked(3).forEach { rowButtons ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowButtons.forEach { icon ->
                                CircleButton(icon = icon,
                                    onClick = { /* Handle button click */ },
                                    onRemove = if (showButtonList) {
                                        {
                                            selectedButtons.remove(icon)
                                            homeVM.saveSelectedButtons(context, selectedButtons)
                                            if (allUserButtons.map { it.first }.contains(icon)) {
                                                availableUserButtons.add(allUserButtons.first { it.first == icon })
                                            } else {
                                                availableShoppingButtons.add(allShoppingButtons.first { it.first == icon })
                                            }
                                        }
                                    } else null)
                            }
                        }
                    }
                }
            }

            RectangularButton(icon = Icons.Default.Add, text = "Agregar acceso directo") {
                showButtonList = !showButtonList
            }

            if (showButtonList) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircleButtonList(
                        userButtons = allUserButtons.filter { it.first !in selectedButtons },
                        shoppingButtons = allShoppingButtons.filter { it.first !in selectedButtons }
                    ) { icon ->
                        if (selectedButtons.size < 6) {
                            selectedButtons.add(icon)
                            homeVM.saveSelectedButtons(context, selectedButtons)
                        }
                    }
                }
            }

            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

            Subtitle("Recomendados")

            if (isConnected) {
                when (postListViewState) {
                    is ViewState.Loading -> LoadingScreen()
                    is ViewState.Error -> ShowErrorMessage((postListViewState as ViewState.Error).message)
                    is ViewState.Success -> {
                        if (selectedPosts.isEmpty()) {
                            ShowNoPostsMessage("No posts available")
                        } else {
                            HorizontalPager(
                                count = selectedPosts.size,
                                state = pagerState,
                                modifier = Modifier.fillMaxWidth()
                            ) { page ->
                                PostCard(selectedPosts[page], navController)
                            }

                            PagerIndicator(
                                totalDots = selectedPosts.size,
                                selectedIndex = pagerState.currentPage,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            } else {
                ShowErrorMessage("Not Connected to Internet ...")
            }

            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

            Subtitle("Nuestra idea")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\"Por cada producto que adquieras,\notra toalla es donada\"",
                    fontFamily = bellefair,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Carousel()
        }
    }
}

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