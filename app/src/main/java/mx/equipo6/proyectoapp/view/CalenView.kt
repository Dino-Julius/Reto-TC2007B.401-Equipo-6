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
import java.util.*

@Composable
fun CalenView(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedDateInMillis by remember { mutableStateOf(0L) } // Store date in millis
    var calculatedDate by remember { mutableStateOf("") }

    // State to control when to show the pop up
    var showDialog by remember { mutableStateOf(false) }

    // Retrieve the context (Where the dates are saved in the system)
    val context = LocalContext.current

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
                // Calendar and Date Display
                EmbeddedCalendarView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onDateSelected = { dateInMillis ->
                        val calendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
                        selectedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
                        selectedDateInMillis = dateInMillis // Save the selected date in millis
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Button to Calculate 30 Days From Selected Date
                Button(
                    onClick = {
                        // Calculate 30 days from the selected date
                        val calendar = Calendar.getInstance().apply {
                            if (selectedDateInMillis != 0L) {
                                timeInMillis = selectedDateInMillis // Use selected date instead of today
                                add(Calendar.DAY_OF_YEAR, 28) // Add 28 days
                            }
                        }
                        calculatedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD5507C) // Set button color to #D5507C
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(text = "Calcular Ciclo Menstural")
                }

                // Button to mark sexual activity
                Button(
                    onClick = {
                        if (selectedDateInMillis != 0L) {
                            // Save the selected date using SharedPreferences
                            saveSexualActivityDate(context, dateInMillis = selectedDateInMillis)
                            // Trigger the pop up to show saved dates
                            showDialog = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD5507C) // Set button color to #D5507C
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)

                ) {
                    Text(text = "Marcar Actividad sexual"

                    )
                }
            }

            // Display the selected date and calculated date
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
                    text = "Fecha estimada próximo ciclo menstrual: $calculatedDate",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                )
            }
        }
    }

    // Show the pop-up dialog when marking a sexual activity
    if (showDialog) {
        SavedDatesDialog(
            context = context,
            onDismiss = { showDialog = false } // Close the dialog
        )
    }
}

@Composable
fun EmbeddedCalendarView(modifier: Modifier = Modifier, onDateSelected: (Long) -> Unit) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            CalendarView(context).apply {
                //Set the background color
                setBackgroundColor(android.graphics.Color.parseColor("#F4D0CB"))

                // Set the date to today
                date = Date().time

                // Set the listener for when the date changes
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    onDateSelected(calendar.timeInMillis)
                }
            }
        }
    )
}

// Composable to show a dialog with the saved dates
@Composable
fun SavedDatesDialog(
    context: Context,
    onDismiss: () -> Unit
) {
    // Get the list of saved dates from SharedPreferences
    val savedDates = remember { getSavedDates(context) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Saved Sexual Activity Dates") },
        text = {
            LazyColumn {
                items(savedDates) { date ->
                    Text(text = date, modifier = Modifier.padding(8.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD5507C) // Set button color to #D5507C
                )) {
                Text("Close")

            }

        }
    )
}

// Function to save the date of sexual activity to SharedPreferences
fun saveSexualActivityDate(context: Context, dateInMillis: Long) {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putLong("sexualActivityDate_${dateInMillis}", dateInMillis)
        apply()
    }
}

// Function to retrieve all saved dates from SharedPreferences
fun getSavedDates(context: Context): List<String> {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.all
        .mapNotNull { entry ->
            val dateInMillis = entry.value as? Long ?: return@mapNotNull null
            val calendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
            "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        }
}
