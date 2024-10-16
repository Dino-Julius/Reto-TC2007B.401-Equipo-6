package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.ui.theme.RetoAppTheme
import mx.equipo6.proyectoapp.view.sampledata.NavigationBars
import mx.equipo6.proyectoapp.viewmodel.AboutUsVM
import mx.equipo6.proyectoapp.viewmodel.CalenVM
import mx.equipo6.proyectoapp.viewmodel.ChatBotViewModel
import mx.equipo6.proyectoapp.viewmodel.HomeVM
import mx.equipo6.proyectoapp.viewmodel.PostVM
import mx.equipo6.proyectoapp.viewmodel.ProductVM
import mx.equipo6.proyectoapp.viewmodel.SignUpViewModel

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
fun AppPrincipal(modifier: Modifier = Modifier, homeVM: HomeVM = viewModel(), aboutUsVM: AboutUsVM = viewModel(),
                 productVM: ProductVM = viewModel(), postVM: PostVM = viewModel(), calenVM: CalenVM = viewModel(),
                 chatBotViewModel: ChatBotViewModel = viewModel()) {
    val navController = rememberNavController()
    RetoAppTheme {
        Scaffold(
            topBar = { NavigationBars().ExperimentalTopAppBar(navController, "ZAZIL")},
            bottomBar = { NavigationBars().AppBottomBar(navController) }
        ) { innerPadding ->
            AppNavHost(
                modifier.padding(innerPadding),
                homeVM,
                aboutUsVM,
                productVM,
                postVM,
                calenVM,
                chatBotViewModel,
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
    productVM: ProductVM,
    postVM: PostVM,
    calenVM: CalenVM,
    chatBotVM: ChatBotViewModel,
    navController: NavHostController,
    signUpViewModel: SignUpViewModel = viewModel()
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
            //HomeView(homeVM, navController, postVM)
            HomeView(homeVM, navController, postVM ,signUpViewModel)
        }

        composable(Windows.ROUTE_COMUNITY) {
            CommunityView(postVM, navController)
        }

        composable(Windows.ROUTE_COMUNITY + "/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            val post = postVM.getPostById(postId?.toIntOrNull()) // Implementa esta función en tu ViewModel
            PostContentView(post, navController, postVM)
        }

        composable(Windows.ROUTE_STORE) {
            ShopView(productVM, navController)
        }

        composable(Windows.ROUTE_STORE + "/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = productVM.getProductById(productId) // Implementa esta función en tu ViewModel
            ProductDetailView(product, navController, productVM)
        }

        composable(Windows.ROUTE_CALENDAR) {
            CalenView(calenVM)
        }

        composable(Windows.ROUTE_CART) {
            ShoppingCartView(productVM, navController)
        }

        composable(Windows.ROUTE_CHATBOT) {
            ChatBotView(chatBotVM)
        }

        composable(Windows.ROUTE_CONFIG) {
            ConfigView(signUpViewModel, navController)
        }

        composable(Windows.ROUTE_CONFIG_MENU) {
            ConfigMenuView(navController)
        }

        composable(Windows.ROUTE_TICKET) {
            TicketView(navController, modifier, productVM, signUpViewModel)
        }

        composable(Windows.ROUTE_FAVORITE_POSTS) {
            FavoritePostsView(postVM, navController)
        }

        composable(Windows.ROUTE_FAVORITE_PRODUCTS) {
            FavoriteProductsView(productVM, navController)
        }

        composable(Windows.ROUTE_DEV_TEAM) {
            DevTeamView(navController)
        }
    }
}