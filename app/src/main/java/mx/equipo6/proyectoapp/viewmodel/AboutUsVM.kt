package mx.equipo6.proyectoapp.viewmodel

import androidx.lifecycle.ViewModel
import mx.equipo6.proyectoapp.model.HomeModel

/**
 * ViewModel de la pantalla "¿Quiénes Somos?" de la aplicación
 * @autor Equipo 6
 */
class AboutUsVM: ViewModel() {
    // Modelo
    val aboutUS = HomeModel()
}