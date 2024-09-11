// Package declaration
package mx.equipo6.proyectoapp.include

// Sealed class to represent different states of a view
sealed class ViewState<out T> {
    // Success state with data of type T
    data class Success<T>(val data: T) : ViewState<T>()

    // Error state with a message
    data class Error(val message: String) : ViewState<Nothing>()

    // Loading state with no data
    data object Loading : ViewState<Nothing>()
}