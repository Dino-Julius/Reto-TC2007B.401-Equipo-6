package mx.equipo6.proyectoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import mx.equipo6.proyectoapp.view.AppPrincipal
import mx.equipo6.proyectoapp.viewmodel.AboutUsVM
import mx.equipo6.proyectoapp.viewmodel.HomeVM

class MainActivity : ComponentActivity() {
    private val homeVM: HomeVM by viewModels()
    private val aboutUsVM: AboutUsVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPrincipal(homeVM, aboutUsVM)
        }
    }
}