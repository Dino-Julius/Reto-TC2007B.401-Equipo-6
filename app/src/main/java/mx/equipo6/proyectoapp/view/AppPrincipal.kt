package mx.equipo6.proyectoapp.view

import AboutUsView
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.ui.theme.RetoAppTheme
import mx.equipo6.proyectoapp.view.sampledata.NavigationBars
import mx.equipo6.proyectoapp.viewmodel.AboutUsVM
import mx.equipo6.proyectoapp.viewmodel.HomeVM
import mx.equipo6.proyectoapp.viewmodel.ProductVM

val bellefair = FontFamily(Font(R.font.bellefair_regular))

/**
 * AppPrincipal: Composable que define la estructura principal de la aplicación.
 * @author Equipo 6
 * @param homeVM ViewModel de la página principal.
 * @param aboutUsVM ViewModel de la página "Acerca de Nosotros".
 * @param productVM ViewModel de la página de productos.
 * @param modifier Modificador de diseño.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun AppPrincipal(homeVM: HomeVM, aboutUsVM: AboutUsVM, productVM: ProductVM, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    RetoAppTheme {
        Scaffold(
//            topBar = { NavigationBars().AppTopBar(
//                onLeftButtonClick = { Log.d("check", "Botón del lado izquierdo") },
//                onRightButtonClick = { Log.d("check", "Botón del lado derecho") }
//            ) },
            topBar = { NavigationBars().ExperimentalTopAppBar("ZAZIL")},
            bottomBar = { NavigationBars().AppBottomBar(navController) }
        ) { innerPadding ->
            AppNavHost(
                innerPadding,
                modifier.padding(innerPadding),
                homeVM,
                aboutUsVM,
                productVM,
                navController
            )
        }
    }
}

@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    homeVM: HomeVM,
    aboutUsVM: AboutUsVM,
    productVM: ProductVM,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Windows.ROUTE_HOME,
        modifier = modifier.fillMaxSize()
    ) {
        composable(Windows.ROUTE_ABOUTUS) {
            AboutUsView(modifier, aboutUsVM)
        }
        composable(Windows.ROUTE_HOME) {
            HomeView(modifier, homeVM)
        }
        composable(Windows.ROUTE_COMUNITY) {
            ComunityView(modifier)
        }
        composable(Windows.ROUTE_STORE) {
            ShopView(innerPadding, productVM, navController)
        }
        composable(Windows.ROUTE_STORE + "/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = productVM.getProductById(productId?.toIntOrNull()) // Implementa esta función en tu ViewModel
            ProductDetailView(product, navController)
        }
        composable(Windows.ROUTE_CALENDAR) {
            CalenView(modifier)
        }

    }
}