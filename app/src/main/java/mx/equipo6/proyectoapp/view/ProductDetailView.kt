package mx.equipo6.proyectoapp.view

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import mx.equipo6.proyectoapp.model.products.Products
import mx.equipo6.proyectoapp.view.sampledata.Stepper
import mx.equipo6.proyectoapp.viewmodel.ProductVM

/**
 * ProductDetailView: Muestra los detalles del producto.
 * @author Julio Vivas; Ulises Jaramillo Portilla | A01798380.
 * @param products Producto a mostrar.
 * @param navController Controlador de navegación.
 * @param productVM ViewModel de productos.
 */
@Composable
fun ProductDetailView(products: Products?, navController: NavHostController, productVM: ProductVM) {
    val context = LocalContext.current
    val quantityToAdd = remember { mutableIntStateOf(1) } // Valor por defecto es 1

    LocalContext.current as Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Fila superior con botón de retroceso y calificación del producto
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 15.dp, top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color(0x8DE7E1E1), shape = CircleShape)
                    .clip(CircleShape)
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .width(70.dp)
                    .background(color = Color(0x8DE7E1E1), shape = RoundedCornerShape(8.dp))
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = products?.rating.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Image(
                    imageVector = Icons.Default.Star, contentDescription = null
                )
            }
        }

        // Imagen del producto
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(300.dp), painter = rememberAsyncImagePainter(
                model = products?.image_path, contentScale = ContentScale.Crop
            ), contentDescription = products?.name
        )
        Spacer(
            modifier = Modifier
                .height(50.dp)
                .background(Color(0x8DE7E1E1))
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .background(
                    Color.White, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                )
        ) {
            // Sección de detalles del producto
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x8DE7E1E1)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(15.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = products?.name ?: "Nombre no disponible",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.Red),
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        Color(0x75F44336), shape = RoundedCornerShape(
                                            topStart = 20.dp, bottomStart = 20.dp
                                        )
                                    )
                                    .padding(10.dp)
                                    .weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = products?.description ?: "Descripción no disponible", fontSize = 16.sp, color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // Sección de cantidad y botón de agregar al carrito
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row{
                    // Mostrar la cantidad actual con un stepper
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(15.dp)
                    ) {
                        Text("Cantidad a agregar:", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Stepper para elegir la cantidad a agregar
                        Stepper(
                            value = quantityToAdd.intValue,
                            onValueChange = { quantityToAdd.intValue = it },
                            minValue = 1,
                            maxValue = 100
                        )
                    }

                    // Botón de agregar al carrito
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .width(200.dp)
                            .padding(top = 30.dp, bottom = 30.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        onClick = {
                            productVM.addItemToCart(products!!, quantityToAdd.intValue) // Pasar la cantidad seleccionada

                            Toast.makeText(
                                context, "${quantityToAdd.intValue} producto(s) agregado(s) al carrito", Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        Text(text = "Agregar al carrito")
                    }
                }
            }
        }
    }
}