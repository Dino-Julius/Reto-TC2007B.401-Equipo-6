package mx.equipo6.proyectoapp.view

import AboutUsView
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.ui.theme.RetoAppTheme
import mx.equipo6.proyectoapp.viewmodel.AboutUsVM
import mx.equipo6.proyectoapp.viewmodel.HomeVM
import mx.equipo6.proyectoapp.view.NavigationBars

val bellefair = FontFamily(Font(R.font.bellefair_regular))

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
            topBar = { NavigationBars().AppTopBar(
                onLeftButtonClick = { Log.d("check", "Botón del lado izquierdo") },
                onRightButtonClick = { Log.d("check", "Botón del lado derecho") }
            ) },
            bottomBar = { NavigationBars().AppBottomBar(navController) }
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