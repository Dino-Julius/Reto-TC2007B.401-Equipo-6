package mx.equipo6.proyectoapp.model.stripeAPI

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

// Function to send sold items to the server
var isSending = false // Add a flag to check if request is already being sent

suspend fun sendSoldItemsToServer(soldItems: List<Map.Entry<Products, Int>>, address: String, email: String) {
    if (isSending) return // Don't send the request if one is already in progress

    isSending = true // Set the flag to true when starting the request

    // Log soldItems to check if it is populated correctly
    Log.d("OrderSender", "Preparing to send the following items:")
    soldItems.forEach { entry ->
        Log.d("OrderSender", "SKU: ${entry.key.sku}, Quantity: ${entry.value}")
    }

    // Create the JSON body for the request
    val jsonBody = JSONObject().apply {
        put("address", address)
        put("email", email)

        // Convert soldItems to JSON array
        val itemsArray = JSONArray()
        soldItems.forEach { entry ->
            val jsonItem = JSONObject().apply {
                put("sku", entry.key.sku)        // Access the SKU from the product
                put("quantity", entry.value)     // Access the quantity
            }
            itemsArray.put(jsonItem)
        }
        put("items", itemsArray)
    }

    // Prepare the request body
    val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody.toString())

    // Build the request
    val request = Request.Builder()
        .url("http://104.248.55.22:3000/api/receive-order") // Replace with your server URL
        .post(requestBody)
        .build()

    // OkHttp client
    val client = OkHttpClient()

    try {
        // Use a coroutine to send the request
        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        // Log the response
        if (response.isSuccessful) {
            response.body?.string()?.let {
                Log.d("OrderSender", "Order sent successfully: $it")
            } ?: Log.e("OrderSender", "Empty response body")
        } else {
            Log.e("OrderSender", "Failed to send order: ${response.code} - ${response.message}")
        }
    } catch (e: Exception) {
        Log.e("OrderSender", "Error sending order: ${e.localizedMessage}")
    } finally {
        isSending = false // Reset the flag when done
    }
}
