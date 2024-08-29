package mx.equipo6.proyectoapp.view

import AboutUsView
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.equipo6.proyectoapp.ui.theme.RetoAppTheme
import mx.equipo6.proyectoapp.viewmodel.AboutUsVM
import mx.equipo6.proyectoapp.viewmodel.HomeVM

/**
 * AppPrincipal: Composable que define la estructura principal de la aplicación.
 *
 * @param  ViewModel de la aplicación.
 * @param modifier Modificador de diseño.
 */
@Composable
fun AppPrincipal(homeVM: HomeVM, aboutUsVM: AboutUsVM, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    RetoAppTheme {
        Scaffold(
            topBar = { AppTopBar(
                onLeftButtonClick = { Log.d("check", "Botón del lado izquierdo") },
                onRightButtonClick = { Log.d("check", "Botón del lado derecho") }
            ) },
            bottomBar = { AppBottomBar(navController) }
        ) { innerPadding ->
            AppNavHost(
                modifier.padding(innerPadding),
                homeVM,
                aboutUsVM,
                navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onLeftButtonClick: () -> Unit,  // Acción para el botón izquierdo
    onRightButtonClick: () -> Unit  // Acción para el botón derecho
) {
    Surface(
        color = MaterialTheme.colorScheme.primary, // Color de fondo
        shape = MaterialTheme.shapes.medium.copy(bottomEnd = CornerSize(16.dp), bottomStart = CornerSize(16.dp)), // Esquinas inferiores redondeadas
        modifier = Modifier.fillMaxWidth() //.height(105.dp)
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(), //.padding(top = 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ZAZIL",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onLeftButtonClick,
                    // modifier = Modifier.padding(top = 25.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Home, // Puedes cambiar el ícono
                        contentDescription = "Menú",
                        tint = Color.White,
                        modifier = Modifier.size(200.dp)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = onRightButtonClick,
                    // modifier = Modifier.padding(top = 25.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle, // Puedes cambiar el ícono
                        contentDescription = "Configuración",
                        tint = Color.White,
                        modifier = Modifier.size(200.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFD5507C) // Hacer transparente el color de fondo del TopAppBar
            )
        )
    }
}

@Composable
fun AppBottomBar(navController: NavHostController) {
    Surface(
        color = MaterialTheme.colorScheme.primary, // Color de fondo
        shape = MaterialTheme.shapes.medium.copy(topEnd = CornerSize(16.dp), topStart = CornerSize(16.dp)), // Esquinas superiores redondeadas
        modifier = Modifier.fillMaxWidth()
    ) {
        BottomAppBar(
            containerColor = Color(0xFFD5507C) // Hacer transparente el color de fondo del BottomAppBar

        ) {
            val navigationPile by navController.currentBackStackEntryAsState()
            val actualScreen = navigationPile?.destination

            // Generar el menú inferior
            Windows.listaPantallas.forEach { pantalla ->
                NavigationBarItem(
                    selected = pantalla.route == actualScreen?.route,
                    onClick = {
                        navController.navigate(pantalla.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text(text = pantalla.etiqueta) },
                    icon = {
                        Icon(
                            imageVector = pantalla.icono,
                            contentDescription = pantalla.etiqueta,
                            modifier = Modifier.size(35.dp)
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, // Color del ícono seleccionado
                        selectedTextColor = Color.White, // Color del texto seleccionado
                        indicatorColor = Color.White, // Color del indicador
                        unselectedIconColor = Color.White, // Color del ícono no seleccionado
                        unselectedTextColor = Color.White // Color del texto no seleccionado

                    )
                )
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    homeVM: HomeVM,
    aboutUsVM: AboutUsVM,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Windows.ROUTE_HOME,
        modifier = modifier.fillMaxSize()) {
        // Lista de pantallas de la aplicación
        composable(Windows.ROUTE_ABOUTUS) {
            AboutUsView(modifier, aboutUsVM)
        }
//        composable(Pantallas.RUTA_CHATBOT) {
//            ChatBotView(modifier, chatBotVM)
//        }
        composable(Windows.ROUTE_HOME) {
            HomeView(modifier, homeVM)
        }
        // Botón Comunidad
        composable(Windows.ROUTE_COMUNITY) {
            ComunityView(modifier)
        }
        // Botón Calendario
//        composable(Pantallas.RUTA_CALENDAR) {
//            CalendarView(modifier)
//        }
    }
}