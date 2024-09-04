package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

class NavigationBars {
    @Composable
    fun AppBottomBar(navController: NavHostController) {
        Surface(
            color = MaterialTheme.colorScheme.primary, // Color de fondo
            modifier = Modifier.fillMaxWidth()
        ) {
            BottomAppBar(
                containerColor = Color(0xFFC7A8BC) // Hacer transparente el color de fondo del BottomAppBar

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



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppTopBar(
        onLeftButtonClick: () -> Unit,  // Acción para el botón izquierdo
        onRightButtonClick: () -> Unit  // Acción para el botón derecho
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(2.dp, Color(0xFFFFFFFF)), // Border with specified hex color
                    shape = RoundedCornerShape(12.dp) // Rounded corners for the border
                )
                .background(Color(0xFFC7A8BC)) // Background color
        ) {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ZAZIL",
                            fontFamily = bellefair,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Thin,
                            fontSize = 40.sp,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onLeftButtonClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home, // TODO CHANGE UGLY ASS ICON
                            contentDescription = "Menú",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onRightButtonClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle, // TODO CHANGE UGLY ASS ICON
                            contentDescription = "Configuración",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFC7A8BC)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp) // Ensure no extra padding
            )
        }
    }
}