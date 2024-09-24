package mx.equipo6.proyectoapp.view

import android.content.Context
import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.equipo6.proyectoapp.viewmodel.CalenVM
import java.util.*

@Composable
fun CalenView(modifier: Modifier = Modifier, viewModel: CalenVM) {
    val context = LocalContext.current

    // Observar el estado reactivo del ViewModel
    val selectedDate by viewModel.selectedDate
    val calculatedDate by viewModel.calculatedDate
    val showDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFC7A8BC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        ) {
            // Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Calendario",
                    fontSize = 40.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Calendar View para seleccionar fecha
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                EmbeddedCalendarView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onDateSelected = { dateInMillis ->
                        viewModel.updateSelectedDate(dateInMillis)
                    }
                )
            }

            // Botones
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Botón para calcular 28 días
                Button(
                    onClick = {
                        viewModel.calculateNextCycle()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD5507C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(text = "Calcular Ciclo Menstrual")
                }

                // Botón para marcar actividad sexual
                Button(
                    onClick = {
                        viewModel.saveDate(context)
                        showDialog.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD5507C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(text = "Marcar Actividad Sexual")
                }
            }

            // Mostrar la fecha seleccionada y la fecha calculada
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Fecha Seleccionada: $selectedDate",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(2.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Fecha Estimada Próximo Ciclo Menstrual: $calculatedDate",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                )
            }
        }
    }

    // Mostrar pop up de fechas guardadas si está activado
    if (showDialog.value) {
        SavedDatesDialog(
            context = context,
            viewModel = viewModel,
            onDismiss = { showDialog.value = false }
        )
    }
}
@Composable
fun SavedDatesDialog(
    context: Context,
    viewModel: CalenVM,
    onDismiss: () -> Unit
) {
    // Observar las fechas guardadas
    val savedDates by viewModel.savedDates

    LaunchedEffect(Unit) {
        viewModel.loadSavedDates(context) // Cargar las fechas guardadas al mostrar el diálogo
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD5507C))
            ) {
                Text("Cerrar", color = Color.White)
            }
        },
        title = {
            Text(
                text = "Fechas Guardadas",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF333333) // Color del texto del título
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp) // Espaciado dentro del pop up
                    .fillMaxWidth()
            ) {
                items(savedDates) { date ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp), // Espaciado entre elementos de la lista
                        shape = MaterialTheme.shapes.medium, // Esquinas redondeadas
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = date,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF555555) // Color del texto
                        )
                    }
                }
            }
        },
        shape = MaterialTheme.shapes.large // Esquinas redondeadas del pop up
    )
}
@Composable
fun EmbeddedCalendarView(modifier: Modifier = Modifier, onDateSelected: (Long) -> Unit) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            CalendarView(context).apply {
                setBackgroundColor(android.graphics.Color.parseColor("#F4D0CB")) // Establecer color de fondo
                date = Date().time // Establecer la fecha actual

                // Listener para cuando el usuario cambia la fecha
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    onDateSelected(calendar.timeInMillis) // Pasar la fecha seleccionada en milisegundos
                }
            }
        }
    )
}







