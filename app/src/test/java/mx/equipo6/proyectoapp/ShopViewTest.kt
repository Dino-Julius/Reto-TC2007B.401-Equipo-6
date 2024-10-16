//package mx.equipo6.proyectoapp
//
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runTest
//import mx.equipo6.proyectoapp.model.products.ProductList
//import mx.equipo6.proyectoapp.model.products.ProductRespository
//import mx.equipo6.proyectoapp.model.products.Products
//import mx.equipo6.proyectoapp.viewmodel.ProductVM
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mockito.*
//import org.junit.Rule
//import org.mockito.Mock
//import org.mockito.junit.MockitoJUnit
//
///**
// * ViewModel para gestionar los productos y el carrito de compras.
// * @author Ulises Jaramillo Portilla | A01798380
// * @param productRespository Repositorio de productos
// * @param context Contexto de la aplicación
// */
//class ProductVMTest {
//
//    @get:Rule
//    val mockitoRule = MockitoJUnit.rule()
//
//    // Mocks
//    @Mock
//    private lateinit var mockProductRepository: ProductRespository
//
//    private lateinit var viewModel: ProductVM
//
//    @Before
//    fun setup() {
//        viewModel = ProductVM(mockProductRepository)
//    }
//
//    @Test
//    fun `addItemToCart should increase product quantity`() = runTest {
//        // Arrange: Crear un producto
//        val product = Products("1", "Producto A", 10.0, "Descripción A", "10x10x10", "", "Categoria A", 0.0)
//        // Act: Agregar el producto al carrito dos veces
//        viewModel.addItemToCart(product, 2)
//
//        // Assert: Verificar que la cantidad en el carrito sea 2
//        val cartItems = viewModel.cartItems.first()
//        assertEquals(1, cartItems.size)
//        assertEquals(2, cartItems[product])
//    }
//
//    @Test
//    fun `removeItemFromCart should decrease product quantity`() = runTest {
//        // Arrange: Agregar un producto al carrito con una cantidad de 3
//        val product = Products("1", "Producto A", 10.0, "Descripción A", "10x10x10", "", "Categoria A", 0.0)
//        viewModel.addItemToCart(product, 3)
//
//        // Act: Eliminar 1 del producto del carrito
//        viewModel.removeItemFromCart(product, 1)
//
//        // Assert: Verificar que la cantidad sea 2
//        val cartItems = viewModel.cartItems.first()
//        assertEquals(2, cartItems[product])
//    }
//
//    @Test
//    fun `removeItemFromCart should remove product when quantity is zero`() = runTest {
//        // Arrange: Agregar un producto al carrito con una cantidad de 2
//        val product = Products("1", "Producto A", 10.0, "Descripción A", "10x10x10", "", "Categoria A", 0.0)
//        viewModel.addItemToCart(product, 2)
//
//        // Act: Eliminar 2 unidades del producto
//        viewModel.removeItemFromCart(product, 2)
//
//        // Assert: Verificar que el producto se haya eliminado del carrito
//        val cartItems = viewModel.cartItems.first()
//        assertFalse(cartItems.containsKey(product))
//    }
//
//    @Test
//    fun `getProductById should return correct product`() = runTest {
//        // Arrange: Configurar el repositorio para devolver una lista de productos
//        val productList = ProductList().apply {
//            addAll(
//                listOf(
//                    Products("1", "Producto A", 10.0, "Descripción A", "10x10x10", "", "Categoria A", 0.0),
//                    Products("2", "Producto B", 20.0, "Descripción B", "20x20x20", "", "Categoria B", 0.0),
//                    Products("3", "Producto C", 30.0, "Descripción C", "30x30x30", "", "Categoria C", 0.0)
//                )
//            )
//        }
//        `when`(mockProductRepository.getProducts()).thenReturn(productList)
//
//        // Act: Llamar a refreshProducts y obtener el producto por ID
//        viewModel.refreshProducts()
//        val product = viewModel.getProductById("1")
//
//        // Assert: Verificar que el producto devuelto sea el correcto
//        assertNotNull(product)
//        assertEquals("Producto A", product?.name)
//    }
//}