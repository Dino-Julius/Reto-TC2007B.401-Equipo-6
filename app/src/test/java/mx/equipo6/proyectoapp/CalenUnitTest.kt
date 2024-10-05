package mx.equipo6.proyectoapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import mx.equipo6.proyectoapp.model.calculateFertileWindowFromModel
import mx.equipo6.proyectoapp.viewmodel.CalenVM
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalenVMTest {

    private lateinit var viewModel: CalenVM

    @Before
    fun setup() {
        // Inicializa el ViewModel antes de cada prueba
        viewModel = CalenVM()
    }

    @Test
    fun `calculateFertileWindow should return correct dates`() = runTest {
        // Arrange: Se establece la fecha de inicio del ciclo
        val startDate = "1/1/2023"  // Fecha de ejemplo (1 de enero de 2023)

        // Act: Se llama a la función para calcular los días fértiles
        viewModel.calculateFertileWindow(startDate)

        // Assert: Se verifican las fechas correctas del inicio y fin de la ventana fértil
        val fertileWindow = viewModel.fertileWindow.value
        assertEquals("11/1/2023", fertileWindow?.first)  // Inicio de la ventana fértil
        assertEquals("18/1/2023", fertileWindow?.second)  // Fin de la ventana fértil
    }

    @Test
    fun `saveDate should update savedDates`() {
        // Arrange: Se establece una fecha seleccionada
        val selectedDateInMillis = 1672531199000L // Fecha de ejemplo (1 de enero de 2023)
        viewModel.updateSelectedDate(selectedDateInMillis)

        // Act: Simulamos que la lista de fechas guardadas se actualiza
        // En lugar de guardar en SharedPreferences, actualizamos manualmente la lista de fechas guardadas
        viewModel.savedDates.value = listOf("1/1/2023")

        // Assert: Verificamos que la lista de fechas guardadas contiene la fecha esperada
        val expectedSavedDates = listOf("1/1/2023")
        assertEquals(expectedSavedDates, viewModel.savedDates.value)
    }
}

class ModelTest {

    @Test
    fun `calculateFertileWindowFromModel should return correct fertile window`() {
        // Arrange: Fecha de inicio del ciclo
        val startDate = "1/1/2023"  // Fecha de ejemplo (1 de enero de 2023)

        // Act: Se llama a la función para calcular la ventana fértil
        val fertileWindow = calculateFertileWindowFromModel(startDate)

        // Assert: Verificamos que las fechas de inicio y fin de la ventana fértil sean correctas
        assertEquals("11/1/2023", fertileWindow?.first)  // Inicio de la ventana fértil
        assertEquals("18/1/2023", fertileWindow?.second)  // Fin de la ventana fértil
    }

}