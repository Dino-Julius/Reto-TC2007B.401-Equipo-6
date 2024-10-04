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
 * Composable que define la vista del "Carrito" de la aplicación.
 * @param modifier Modificador de diseño.
 */
@Preview(showBackground = true, widthDp = 400)
@Composable
fun CartView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Title("Página del carrito desde el CartView")
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
    }
}