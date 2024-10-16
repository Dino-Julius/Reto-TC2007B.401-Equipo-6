package mx.equipo6.proyectoapp.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.include.ViewState
import mx.equipo6.proyectoapp.model.products.ProductList
import mx.equipo6.proyectoapp.model.products.ProductRespository
import mx.equipo6.proyectoapp.model.products.Products
import mx.equipo6.proyectoapp.model.stripeAPI.sendSoldItemsToServer
import mx.equipo6.proyectoapp.network_di.NetworkChangeReceiver.NetworkChangeReceiver.isNetworkConnected
import javax.inject.Inject

/**
 * ViewModel para mostrar el inverntario de productos
 * @author Julio Vivas; Ulises Jaramillo Portilla; Jesus Guzman Ortega.
 * @param productRespository ProductRespository
 * @param context Context
 */
@HiltViewModel
class ProductVM @Inject constructor(
    private val productRespository: ProductRespository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("favorite_products", Context.MODE_PRIVATE)

    private val _product = MutableStateFlow<ViewState<ProductList>>(ViewState.Loading)
    val products : StateFlow<ViewState<ProductList>> get() = _product

    // Map to store products and their quantities
    private val _cartItems = MutableStateFlow<Map<Products, Int>>(emptyMap())
    val cartItems: StateFlow<Map<Products, Int>> get() = _cartItems

    var soldItems: List<Map.Entry<Products, Int>> = listOf()
    var isPaymentSuccessful: Boolean = false

    // LiveData for observ network connection
    private val _isConnected = MutableStateFlow(isNetworkConnected(context))
    val isConnected : StateFlow<Boolean> get() = _isConnected

    // Register the broadcast Receiver
    private val networkChangeReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            _isConnected.value = isNetworkConnected(context = context!!)

            if (_isConnected.value) {
                // If Connected make API Call
                fetchProducts()
            } else {
                // If Not Connected reset the API Call
                _product.value = ViewState.Error("Network error..., Please check your internet connection")
            }
        }
    }

    init {
        // Register the receiver in the init block
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkChangeReceiver, intentFilter)
        fetchProducts()
    }

    fun onFavoriteButtonClicked(productId: String, isFavorite: Boolean) {
        saveFavoriteProduct(productId, isFavorite)
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _product.value = ViewState.Loading
            try {
                val results = productRespository.getProducts()
                loadFavoriteProducts(results)
                _product.value = ViewState.Success(results)
            } catch (e: Exception) {
                _product.value = ViewState.Error("An Error Occurred. Please try Again")
            }
        }
    }

    private fun loadFavoriteProducts(products: ProductList) {
        val favoriteProducts = sharedPreferences.getStringSet("favorites", emptySet()) ?: emptySet()
        products.forEach { product ->
            product.favorite = favoriteProducts.contains(product.sku)
        }
    }

    fun saveFavoriteProduct(productId: String, isFavorite: Boolean) {
        val editor = sharedPreferences.edit()
        val favoriteProducts = sharedPreferences.getStringSet("favorites", mutableSetOf()) ?: mutableSetOf()
        if (isFavorite) {
            favoriteProducts.add(productId.toString())
        } else {
            favoriteProducts.remove(productId.toString())
        }
        editor.putStringSet("favorites", favoriteProducts)
        editor.apply()
    }


    override fun onCleared() {
        super.onCleared()
        // unRegister
        context.unregisterReceiver(networkChangeReceiver)
    }


    // Function to add a product or increase its quantity
    fun addItemToCart(product: Products, quantityToAdd: Int) {
        // Get the current quantity of the product in the cart
        val currentQuantity = _cartItems.value[product] ?: 0

        // Update the cart items state
        _cartItems.value = _cartItems.value.toMutableMap().apply {
            this[product] = currentQuantity + quantityToAdd
        }
    }


    // Function to remove a product or decrease its quantity
    fun removeItemFromCart(product: Products, quantityToRemove: Int) {
        // Get the current quantity of the product in the cart
        val currentQuantity = _cartItems.value[product] ?: 0

        if (currentQuantity > 0) {
            if (currentQuantity <= quantityToRemove) {
                // Remove the product completely if quantity to remove is greater or equal
                _cartItems.value = _cartItems.value - product
            } else {
                // Decrease the quantity
                _cartItems.value = _cartItems.value.toMutableMap().apply {
                    this[product] = currentQuantity - quantityToRemove
                }
            }
        }
    }

    fun refreshProducts() {
        fetchProducts()
    }

    fun clearCart() {
        _cartItems.value = mapOf()
    }


    fun getProductById(productId: String?): Products? {
        return (products.value as? ViewState.Success)?.data?.find { it.sku == productId }
    }

    fun placeOrder(address: String, email: String) {
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            try {
                // Log soldItems to check if they are correct
                Log.d("placeOrder", "Preparing to send the following sold items:")
                soldItems.forEach { entry ->
                    Log.d("placeOrder", "SKU: ${entry.key.sku}, Quantity: ${entry.value}")
                }

                // Call the sendSoldItemsToServer suspend function
                sendSoldItemsToServer(soldItems, address, email)
                Log.d("placeOrder", "Order placed successfully")
            } catch (e: Exception) {
                Log.e("placeOrder", "Failed to place order: ${e.localizedMessage}")
            }
        }
    }

}