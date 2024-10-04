package mx.equipo6.proyectoapp.view.sampledata

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.equipo6.proyectoapp.model.products.Products
import mx.equipo6.proyectoapp.viewmodel.ProductVM

@Composable
fun ProductQuantityDialog(
    showDialog: MutableState<Boolean>,
    product: Products,
    quantityToAdd: MutableState<Int>,
    productVM: ProductVM,
    ctx: Context
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false }, // Dismiss the dialog
            title = {
                Text(
                    text = "Agregar productos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Column {
                    Text("¿Cuántos productos quieres agregar?", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Stepper to select the quantity
                    Stepper(
                        value = quantityToAdd.value,
                        onValueChange = { quantityToAdd.value = it },
                        minValue = 1,
                        maxValue = 100 // Adjust this based on your requirements
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        productVM.addItemToCart(product, quantityToAdd.value) // Add product with selected quantity
                        Toast.makeText(
                            ctx,
                            "${quantityToAdd.value} producto(s) agregado(s) al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                        showDialog.value = false // Close the dialog
                    }
                ) {
                    Text("Agregar", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancelar", fontWeight = FontWeight.Bold, color = Color.Gray)
                }
            }
        )
    }
}