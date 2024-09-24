package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import mx.equipo6.proyectoapp.view.sampledata.CircleButton
import mx.equipo6.proyectoapp.view.sampledata.CircleButtonList
import mx.equipo6.proyectoapp.view.sampledata.PagerIndicator
import mx.equipo6.proyectoapp.view.sampledata.RectangularButton
import mx.equipo6.proyectoapp.view.sampledata.SampleCard
import mx.equipo6.proyectoapp.view.sampledata.Subtitle
import mx.equipo6.proyectoapp.viewmodel.HomeVM

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeView(modifier: Modifier = Modifier, homeVM: HomeVM = viewModel()) {
    val adviceList = homeVM.adviceList.collectAsState().value
    val pagerState = rememberPagerState()
    val selectedButtons = remember { mutableStateListOf<ImageVector>() }
    val allUserButtons = listOf(
        Icons.Default.Home,
        Icons.Default.Settings,
        Icons.Default.Favorite
    )
    val allShoppingButtons = listOf(
        Icons.Default.Share,
        Icons.Default.Search,
        Icons.Default.Notifications
    )
    val availableUserButtons = remember { mutableStateListOf(*allUserButtons.toTypedArray()) }
    val availableShoppingButtons = remember { mutableStateListOf(*allShoppingButtons.toTypedArray()) }
    var showButtonList by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp)
        ) {
            Subtitle("¡Bienvenido, @usuario!")
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))

            Subtitle("Accesos directos")
            Spacer(modifier = if (selectedButtons.isEmpty()) Modifier.height(1.dp) else Modifier.height(4.dp)) // Further reduced spacing

            if (selectedButtons.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp) // Entre botones
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
                                            if (allUserButtons.contains(icon)) {
                                                availableUserButtons.add(icon)
                                            } else {
                                                availableShoppingButtons.add(icon)
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
                        userButtons = allUserButtons.filter { it !in selectedButtons },
                        shoppingButtons = allShoppingButtons.filter { it !in selectedButtons }
                    ) { icon ->
                        if (selectedButtons.size < 6) {
                            selectedButtons.add(icon)
                        }
                    }
                }
            }

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

            Subtitle("Consejos del día")

            HorizontalPager(
                count = adviceList.size,
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                SampleCard(
                    texto = adviceList[page].text
                )
            }

            PagerIndicator(
                totalDots = adviceList.size,
                selectedIndex = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

            Subtitle("Accesos rápidos")
        }
    }
}