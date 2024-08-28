package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.equipo6.proyectoapp.viewmodel.HomeVM
import mx.equipo6.proyectoapp.view.sampledata.Subtitle
import mx.equipo6.proyectoapp.view.sampledata.Title

/**
 * HomeView: Composable que define la vista de la página de inicio de la aplicación.
 *
 * @param  ViewModel de la aplicación.
 * @param modifier Modificador de diseño.
 */
@Preview(showBackground = true, widthDp = 400)
@Composable
fun HomeView(modifier: Modifier = Modifier, homeVM: HomeVM = HomeVM()) {
    // Estado de la app
    // val estado = homeVM.estado.collectAsState() // Subscripción al estado de la app
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Title("Página de Inicio desde el HomeView")
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        // Marcadores
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Subtitle(texto = "Prototipo")
            }
            Column(modifier = Modifier.weight(1f)) {
                Subtitle(texto = "Segundo Texto")
            }
        }
    }
}

//@Composable
//fun HomeView(modifier: Modifier = Modifier, navController: NavHostController) {
//    Box (
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column{
//            Text(
//                text = "Vista de Inicio Aplicación",
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}