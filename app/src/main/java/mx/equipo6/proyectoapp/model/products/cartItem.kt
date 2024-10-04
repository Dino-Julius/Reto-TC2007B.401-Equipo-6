package mx.equipo6.proyectoapp.model.products

import androidx.compose.material3.TextButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import mx.equipo6.proyectoapp.view.sampledata.Stepper

/**
 * author: Jesus Guzman Ortega
 * CartItem: Shows the items in the cart.
 * @param products: Product
*  @param quantity: Int
 *  @param onRemoveItem: (Products, Int) -> Unit
 **/

@Composable
fun CartItem(products: Products, quantity: Int, onRemoveItem: (Products, Int) -> Unit) {
    // State variables for dialog and quantity to remove
    val showDialog = remember { mutableStateOf(false) }
    val quantityToRemove = remember { mutableStateOf(1) } // Default to removing 1 item

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(Color(0xFFeee4eb))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "http://104.248.55.22" + products.image_path),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = products.name, fontWeight = FontWeight.Bold, maxLines = 1, fontSize = 16.sp)
                Text(text = "${products.price}", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Cantidad: $quantity", fontSize = 14.sp, color = Color.Gray) // Show current quantity
            }

            IconButton(
                onClick = { showDialog.value = true } // Update showDialog state
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove item",
                    tint = Color(0xFFA97998)
                )
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false }, // Dismiss the dialog
            shape = RoundedCornerShape(16.dp), // Rounded corners for a modern feel
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Eliminar artículo", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            },
            text = {
                Column {
                    Text("¿Cuántos quieres eliminar?", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Stepper(
                        value = quantityToRemove.value,
                        onValueChange = { quantityToRemove.value = it },
                        minValue = 1,
                        maxValue = quantity
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val quantityToRemoveValue = quantityToRemove.value // Get the value
                        if (quantityToRemoveValue > 0 && quantityToRemoveValue <= quantity) {
                            onRemoveItem(products, quantityToRemoveValue) // Pass product and quantity to remove
                            showDialog.value = false // Close the dialog
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp) // Add some padding for spacing
                ) {
                    Text("Eliminar", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Gray
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

