package mx.equipo6.proyectoapp.model.stripeAPI

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.api.RetrofitClient
import mx.equipo6.proyectoapp.model.products.Products

/**
 * Data class que representa la estructura de un pedido
 * @param address Dirección de envío
 * @param email Correo electrónico del cliente
 * @param items Lista de productos en el pedido
 * @author Jesús Guzmán | A01799257
 */
data class OrderRequest(
    val address: String,
    val email: String,
    val items: List<OrderItem>
)

/**
 * Data class que representa la estructura de un producto en un pedido
 * @param sku SKU del producto
 * @param quantity Cantidad de productos
 * @author Jesús Guzmán | A01799257
 */
data class OrderItem(
    val sku: String,
    val quantity: Int
)

// Variable que indica si se está enviando un pedido
var isSending = false

/**
 * Función que envía los productos vendidos al servidor
 * @param soldItems Lista de productos vendidos
 * @param address Dirección de envío
 * @param email Correo electrónico del cliente
 * @author Jesús Guzmán | A01799257
 */
suspend fun sendSoldItemsToServer(soldItems: List<Map.Entry<Products, Int>>, address: String, email: String) {
    if (isSending) return
    isSending = true
    Log.d("OrderSender", "Preparing to send the following items:")
    soldItems.forEach { entry ->
        Log.d("OrderSender", "SKU: ${entry.key.sku}, Quantity: ${entry.value}")
    }

    val items = soldItems.map { entry ->
        OrderItem(sku = entry.key.sku, quantity = entry.value)
    }

    val orderRequest = OrderRequest(address = address, email = email, items = items)

    try {
        val response = withContext(Dispatchers.IO) {
            RetrofitClient.apiService.sendOrder(orderRequest)
        }

        if (response.string().isNotEmpty()) {
            Log.d("OrderSender", "Order sent successfully: ${response.string()}")
        } else {
            Log.e("OrderSender", "Empty response body")
        }
    } catch (e: Exception) {
        Log.e("OrderSender", "Error sending order: ${e.localizedMessage}")
    } finally {
        isSending = false
    }
}