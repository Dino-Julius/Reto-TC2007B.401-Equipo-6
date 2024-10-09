package mx.equipo6.proyectoapp.view

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import mx.equipo6.proyectoapp.view.sampledata.SearchBar
import mx.equipo6.proyectoapp.view.sampledata.Title
import mx.equipo6.proyectoapp.viewmodel.PostVM

/**
 * Composable que muestra la vista de la comunidad.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param postVM ViewModel de los posts.
 */

@Composable
fun CommunityView(postVM: PostVM, navController: NavHostController) {
    val postListViewState by postVM.posts.collectAsState()
    val isConnected by postVM.isConnected.collectAsState()
    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf("") }
    var searchTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        searchTriggered = false
        delay(5000)
        searchTriggered = true
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

        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                modifier = Modifier.padding(bottom = 10.dp),
                onValueChange = { searchQuery = it.text }
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-18).dp)
                        .align(Alignment.BottomCenter),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
                CategoryBar(selectedIndex = 0)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (isConnected) {
                    when (postListViewState) {
                        is ViewState.Success -> {
                            val postList = (postListViewState as ViewState.Success).data
                            val filteredPosts = if (searchTriggered) {
                                postList.filter { post ->
                                    post.title.contains(searchQuery, ignoreCase = true)
                                         //   || post.partner_id.contains(searchQuery, ignoreCase = true)
                                }
                            } else {
                                postList
                            }
                            if (filteredPosts.isEmpty()) {
                                ShowNoPostsMessage()
                            } else {
                                LazyColumn(
                                    state = listState,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    itemsIndexed(filteredPosts) { _, post ->
                                        PostCard(post, navController, postVM)
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

@Composable
private fun ShowNoPostsMessage() {
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
            Text(text = "Aún no hay post en esta categoría")
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