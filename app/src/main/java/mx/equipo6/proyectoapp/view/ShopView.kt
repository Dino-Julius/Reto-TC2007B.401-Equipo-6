package mx.equipo6.proyectoapp.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.model.products.Products
import mx.equipo6.proyectoapp.view.sampledata.ProductQuantityDialog
import mx.equipo6.proyectoapp.viewmodel.ProductVM

/**
 * ShopView: Vista principal de la tienda, muestra los productos en una vista de cuadrícula
 * @author Julio Vivas
 * @param productVM ProductVM
 * @param navController NavHostController
 */

@Composable
fun ShopView(productVM: ProductVM, navController: NavHostController) {
        val productListViewState by productVM.products.collectAsState()
        val isConnected by productVM.isConnected.collectAsState()

        if (isConnected) {
            when(productListViewState) {
                is ViewState.Success -> {
                    // Handle success state
                    val productList = (productListViewState as ViewState.Success).data
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        // modifier = Modifier.padding(modifier),
                        content = {
                            items(productList.size) { index: Int ->
                                ProductsCardUI(productList[index], navController, productVM)
                            }
                        })
                }

                is ViewState.Error -> {
                    val errorMsg =  (productListViewState as ViewState.Error).message
                    ShowErrorMessage(errorMsg)
                }

                is ViewState.Loading -> {
                    LoadingScreen()
                }
            }
        } else {
            ShowErrorMessage("Not Connected to Internet ...")
        }
}

@Composable
private fun ShowErrorMessage(errorMsg: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_connection),
                contentDescription ="img error",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp))
            Text(text = errorMsg)
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun ProductsCardUI(products: Products, navController: NavHostController, productVM: ProductVM) {
    val ctx = LocalContext.current
    val quantityToAdd = remember { mutableIntStateOf(1) }
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .height(270.dp)
            .width(212.dp)
            .padding(6.dp)
            .clickable {
                navController.navigate(Windows.ROUTE_STORE + "/${products.sku}")
                Toast.makeText(ctx, "Product: ${products.name}", Toast.LENGTH_SHORT).show()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp), // Half of the card height
                painter = rememberAsyncImagePainter(model = products.image_path),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp)
            ) {
                Text(
                    text = products.name,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Text(
                    text = products.description,
                    modifier = Modifier.padding(top = 3.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${products.price}",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                showDialog.value = true // Trigger dialog
                            }
                            .clip(shape = CircleShape)
                            .background(Color(0xFFC7A8BC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = "",
                            modifier = Modifier.size(18.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    // Call the dialog function and pass necessary parameters
    ProductQuantityDialog(
        showDialog = showDialog,
        product = products,
        quantityToAdd = quantityToAdd,
        productVM = productVM,
        ctx = ctx
    )
}