package mx.equipo6.proyectoapp.model.stripeAPI

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.api.RetrofitClient
import mx.equipo6.proyectoapp.model.products.Products
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

/**
 *   @author Jesus Guzman
 *   Send the sold items to the server to complete the order to be stored in the database
 *   @param soldItems List of sold items
 *   @param address Address of the user
 *   @param email Email of the user
 */

data class OrderRequest(
    val address: String,
    val email: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val sku: String,
    val quantity: Int
)

var isSending = false

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