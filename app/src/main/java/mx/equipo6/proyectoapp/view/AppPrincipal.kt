package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
            topBar = { AppTopBar() },
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
fun AppTopBar() {
    TopAppBar(
        title = {
            Text(text = "The GOATS APP",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun AppBottomBar(navController: NavHostController) {
    BottomAppBar{
        val pilaNavegación by navController.currentBackStackEntryAsState()
        val pantallaActual = pilaNavegación?.destination

        // Generar el menú inferior
        Pantallas.listaPantallas.forEach { pantalla ->
            NavigationBarItem(selected = pantalla.ruta == pantallaActual?.route,
                onClick = {
                    navController.navigate(pantalla.ruta) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }},
                label = { Text(text = pantalla.etiqueta) },
                icon = {
                    Icon(imageVector = pantalla.icono,
                        contentDescription = pantalla.etiqueta)
                },
                alwaysShowLabel = true)
//            // Si la pantalla actual es igual a la pantalla actual, se pone en negrita
//            val estilo = if (pantalla.ruta == pantallaActual) MaterialTheme.typography.bodyBold else MaterialTheme.typography.body1
//            Text(
//                text = pantalla.etiqueta,
//                style = estilo,
//                modifier = Modifier.weight(1f),
//                textAlign = TextAlign.Center
//            )
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
        startDestination = Pantallas.ROUTE_HOME,
        modifier = modifier.fillMaxSize()) {
        // Lista de pantallas de la aplicación
        composable(Pantallas.ROUTE_ABOUTUS) {
            AboutUsView(modifier, aboutUsVM)
        }
//        composable(Pantallas.RUTA_CHATBOT) {
//            ChatBotView(modifier, chatBotVM)
//        }
        composable(Pantallas.ROUTE_HOME) {
            HomeView(modifier, homeVM)
        }
        // Botón Comunidad
        composable(Pantallas.ROUTE_COMUNITY) {
            ComunityView(modifier)
        }
        // Botón Calendario
//        composable(Pantallas.RUTA_CALENDAR) {
//            CalendarView(modifier)
//        }
    }
}