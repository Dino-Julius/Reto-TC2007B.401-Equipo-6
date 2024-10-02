package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import mx.equipo6.proyectoapp.view.sampledata.Carousel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeView(modifier: Modifier = Modifier, homeVM: HomeVM = viewModel()) {
    val context = LocalContext.current
    val adviceList = homeVM.adviceList.collectAsState().value
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

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp)
        ) {
            Subtitle(
                "¡Bienvenido, @usuario!",
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Divider(color = Color.LightGray, thickness = 0.5.dp)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))

            Spacer(modifier = if (selectedButtons.isEmpty()) Modifier.height(1.dp) else Modifier.height(4.dp))

            if (selectedButtons.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 4.dp),
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

            Divider(color = Color.LightGray, thickness = 0.5.dp)

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

            Divider(color = Color.LightGray, thickness = 0.5.dp)

            Subtitle("Nuestra idea")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\"Por cada producto que adquieras,\notra toalla es donada\"", // \n crea un salto de línea
                    fontFamily = bellefair,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center, // Asegura que el texto se centre correctamente
                    modifier = Modifier.fillMaxWidth() // Añade este modificador para que el texto se ajuste al ancho disponible
                )
            }
            Carousel()
        }
    }
}