package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.equipo6.proyectoapp.view.sampledata.Title

@Composable
fun ConfigMenuView(
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                Title(
                    "Configuración",
                    modifier = Modifier.padding(bottom = 10.dp),
                    textAlign = TextAlign.Start
                )
            }

            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .clickable { navController.navigate(Windows.ROUTE_CONFIG) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp, start = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings, // Replace with your desired default icon
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Text(
                        "Actualización de perfil",
                        modifier = Modifier.padding(start = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }
            }

            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .clickable { navController.navigate(Windows.ROUTE_DEV_TEAM) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp, start = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info, // Replace with your desired default icon
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Text(
                        "Equipo desarrollador",
                        modifier = Modifier.padding(start = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}