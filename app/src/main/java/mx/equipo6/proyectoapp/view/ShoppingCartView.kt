package mx.equipo6.proyectoapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.equipo6.proyectoapp.model.products.CartItem
import mx.equipo6.proyectoapp.viewmodel.ProductVM

/**
 * ShoppingCartView: SHOWS Cart items.
 * @autor Jesus Guzman Ortega
 * @param navController navigation controller
 * @param productVM ViewModel.
 */

@Composable
fun ShoppingCartView(productVM: ProductVM, navController: NavHostController) {
    val cartItems = productVM.cartItems.collectAsState().value // LIST OF THE ITEMS IN THE CART
    // Calculate the total price based on the product price and quantity
    val totalPrice = cartItems.entries.sumOf { (product, quantity) -> product.price * quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "¡Tus próximas compras!",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
        )

        // Check if cart is empty
        if (cartItems.isEmpty()) {
            Text(text = "El carrito está vacío.", fontSize = 20.sp, color = Color.Gray)
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f) // Use weight to allow the LazyColumn to take available space
            ) {
                items(cartItems.entries.toList()) { (product, quantity) ->
                    CartItem(
                        product = product,
                        quantity = quantity,
                        onRemoveItem = { product, quantityToRemove ->
                            productVM.removeItemFromCart(product, quantityToRemove) // Pass both product and quantity to remove
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total: $ ${"%.2f".format(totalPrice)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
            )

            Button(
                onClick = {
                    navController.navigate("Checkout")
                    // Toast.makeText(null, "Compra realizada", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFC7A8BC))
            ) {
                Text(
                    text = "Continuar compra",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}