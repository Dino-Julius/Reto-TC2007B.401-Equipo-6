package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.equipo6.proyectoapp.viewmodel.ProductVM

@Composable
fun TicketView(navController: NavController, modifier: Modifier = Modifier, productVM: ProductVM) {
    val cartItems by productVM.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contenedor del ticket con borde y fondo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)) // Borde del ticket
                .padding(16.dp) // Espacio interior del ticket
                .background(color = Color.White), // Fondo del ticket
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Resumen de tu compra",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp), // Estilo más llamativo para el título
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Si el carrito no está vacío
            if (cartItems.isNotEmpty()) {
                cartItems.forEach { (product, quantity) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Text(text = "Producto: ${product.name}", style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp))
                        Text(text = "Cantidad: $quantity", style = TextStyle(fontSize = 14.sp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Recuerda tomar captura de pantalla de tu ticket",
                    style = TextStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "¡Gracias por tu compra!",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    color = Color(0xFF4CAF50), // Color verde para el agradecimiento
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Limpiar el carrito después de mostrar el ticket
                        productVM.clearCart()
                        // Regresar a la pantalla anterior
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Color del botón
                ) {
                    Text(text = "Regresar a la app", color = Color.White) // Texto blanco en el botón
                }
            } else {
                Text(text = "No se encontraron productos en tu ticket")
            }
        }
    }
}
