package mx.equipo6.proyectoapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mx.equipo6.proyectoapp.view.components.CategoryBar
import mx.equipo6.proyectoapp.view.sampledata.SearchBar
import mx.equipo6.proyectoapp.view.sampledata.Title
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.view.components.PostCard
import mx.equipo6.proyectoapp.viewmodel.PostVM
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.viewmodel.ProductVM

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

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp)
        ) {
            Title(
                "Comunidad",
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(modifier = Modifier.padding(bottom = 10.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Divider(
                    color = Color.LightGray,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-18).dp)
                        .align(Alignment.BottomCenter)
                )
                CategoryBar(selectedIndex = 0, itemWidth = 25f)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (isConnected) {
                    when (postListViewState) {
                        is ViewState.Success -> {
                            val postList = (postListViewState as ViewState.Success).data
                            if (postList.isEmpty()) {
                                ShowNoPostsMessage()
                            } else {
                                LazyColumn(
                                    state = listState,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    itemsIndexed(postList) { index, post ->
                                        PostCard(post,  navController, postVM)
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