package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.equipo6.proyectoapp.view.sampledata.Title

/**
 * HomeView: Composable que define la vista de la página "Comunidad" de la aplicación.
 * @param modifier Modificador de diseño.
 */
@Preview(showBackground = true, widthDp = 400)
@Composable
fun ComunityView(modifier: Modifier = Modifier) {
    // Estado de la app
    // val estado = homeVM.estado.collectAsState() // Subscripción al estado de la app
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Title("Página de Comunidad desde el HomeView")
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
    }
}