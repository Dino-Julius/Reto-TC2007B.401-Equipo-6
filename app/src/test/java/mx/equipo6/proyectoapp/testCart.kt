package mx.equipo6.proyectoapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import mx.equipo6.proyectoapp.model.products.Products
import mx.equipo6.proyectoapp.model.products.Rating
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CartUnitTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var _cartItems: MutableState<Map<Products, Int>>
    private lateinit var product: Products

    @Before
    fun setup() {
        _cartItems = mutableStateOf(emptyMap())
        product = Products(
            category = "Tren",
            description = "Example Product Description",
            id = 1,
            image = "https://example.com/image.jpg",
            price = 100.0,
            rating = Rating(4, 4.0),
            title = "Example Product"
        )
    }

    // Function to add a product or increase its quantity
    fun addItemToCart(product: Products, quantityToAdd: Int) {
        val currentQuantity = _cartItems.value[product] ?: 0
        _cartItems.value = _cartItems.value.toMutableMap().apply {
            this[product] = currentQuantity + quantityToAdd
        }
    }

    // Function to remove a product or decrease its quantity
    fun removeItemFromCart(product: Products, quantityToRemove: Int) {
        val currentQuantity = _cartItems.value[product] ?: 0
        if (currentQuantity > 0) {
            if (currentQuantity <= quantityToRemove) {
                _cartItems.value = _cartItems.value - product
            } else {
                _cartItems.value = _cartItems.value.toMutableMap().apply {
                    this[product] = currentQuantity - quantityToRemove
                }
            }
        }
    }

    @Test
    fun testAddItemToCart() {
        addItemToCart(product, 1)
        assertEquals(1, _cartItems.value[product])

        addItemToCart(product, 2)
        assertEquals(3, _cartItems.value[product])
    }

    @Test
    fun testRemoveItemFromCart() {
        addItemToCart(product, 3)
        removeItemFromCart(product, 1)
        assertEquals(2, _cartItems.value[product])

        removeItemFromCart(product, 2)
        assertEquals(null, _cartItems.value[product])
    }
}