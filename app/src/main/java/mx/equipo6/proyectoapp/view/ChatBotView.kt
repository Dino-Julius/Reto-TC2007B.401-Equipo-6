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
 * Composable que define la vista de la página "ChatBot" de la aplicación.
 * @param modifier Modificador de diseño.
 */
@Preview(showBackground = true, widthDp = 400)
@Composable
fun ChatBotView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Title("Página de ChatBot desde el ChatBotView")
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
    }
}