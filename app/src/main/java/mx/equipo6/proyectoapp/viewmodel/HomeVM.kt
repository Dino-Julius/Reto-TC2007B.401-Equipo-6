package mx.equipo6.proyectoapp.viewmodel

import androidx.lifecycle.ViewModel
import mx.equipo6.proyectoapp.model.HomeModel

/**
 * ViewModel de la pantalla principal de la aplicación
 * @autor Equipo 6
 */
class HomeVM: ViewModel() {
    // Modelo
    val home = HomeModel()
}