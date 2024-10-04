package mx.equipo6.proyectoapp.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import mx.equipo6.proyectoapp.network_di.NetworkChangeReceiver.NetworkChangeReceiver.isNetworkConnected
import javax.inject.Inject

/**
 * ViewModel para mostrar el inverntario de productos
 * @author Julio Vivas
 * @param productRespository ProductRespository
 * @param context Context
 */
@HiltViewModel
class ProductVM @Inject constructor(
    private val productRespository: ProductRespository,
    @ApplicationContext private val context: Context
): ViewModel() {

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

    private fun fetchProducts() {
        viewModelScope.launch {
            _product.value = ViewState.Loading
            try {
                val results = productRespository.getProducts()
                _product.value = ViewState.Success(results)
                Log.e("TAG_SUCCESS", "fetchProducts: ")
            } catch (e: Exception) {
                Log.e("TAG_ERROR", "fetchProducts: ")
                _product.value = ViewState.Error("An Error Occurred. Please try Again")
            }
        }
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

    fun clearCart() {
        _cartItems.value = mapOf()
    }


    fun getProductById(productId: Int?): Products? {
        return (products.value as? ViewState.Success)?.data?.find { it.id == productId }
    }
}