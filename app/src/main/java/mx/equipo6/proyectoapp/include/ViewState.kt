package mx.equipo6.proyectoapp.include

/**
 * Clase sellada para representar diferentes estados de una vista
 * @author Julio Vivas | A01749879
 * @property Success Estado de éxito con datos de tipo T
 * @property Error Estado de error con un mensaje
 * @property Loading Estado de carga sin datos
 */
sealed class ViewState<out T> {
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(val message: String) : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
}