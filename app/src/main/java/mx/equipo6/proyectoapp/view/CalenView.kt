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
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = androidx.compose.ui.graphics.Color(0xFFC7A8BC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        ) {
            // Title
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

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
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

                Button(
                    onClick = {
                        viewModel.saveDate(context)
                        showDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD5507C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(text = "Marcar Actividad sexual")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Fecha Seleccionada: ${viewModel.selectedDate}",
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
                    text = "Fecha estimada próximo ciclo menstrual: ${viewModel.calculatedDate}",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                )
            }
        }
    }

    if (showDialog) {
        SavedDatesDialog(
            context = context,
            viewModel = viewModel,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun EmbeddedCalendarView(modifier: Modifier = Modifier, onDateSelected: (Long) -> Unit) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            CalendarView(context).apply {
                setBackgroundColor(android.graphics.Color.parseColor("#F4D0CB"))
                date = Date().time
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    onDateSelected(calendar.timeInMillis)
                }
            }
        }
    )
}
@Composable
fun SavedDatesDialog(
    context: Context,
    viewModel: CalenVM,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadSavedDates(context)
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
                items(viewModel.savedDates) { date ->
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



