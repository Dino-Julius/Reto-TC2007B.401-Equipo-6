package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.model.products.Products
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
    val totalPrice = cartItems.sumOf { it.price } // SUM OF THE PRICES

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "¡Tus próximas compras!",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = bellefair
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            items(cartItems.size) { index ->
                CartItem(cartItems[index], onRemoveItem = { product ->
                    productVM.removeItemFromCart(product)
                })
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
                fontFamily = bellefair
            )

            Button(
                onClick = { /* TODO CHECKOUT */ },
                colors = ButtonDefaults.buttonColors(Color(0xFFC7A8BC))
            ) {
                Text(text = "Continuar compra",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,)
            }
        }
    }
}

@Composable
fun CartItem(product: Products, onRemoveItem: (Products) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.image),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = product.title, fontWeight = FontWeight.Bold, maxLines = 1, fontSize = 16.sp)
                Text(text = "${product.price}", fontSize = 14.sp, color = Color.Gray)
            }

            IconButton(
                onClick = { onRemoveItem(product) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove item",
                    tint = Color.Red
                )
            }
        }
    }
}
