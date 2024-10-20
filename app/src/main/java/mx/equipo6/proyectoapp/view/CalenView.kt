package mx.equipo6.proyectoapp.view

import android.content.Context
import android.widget.CalendarView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import mx.equipo6.proyectoapp.view.sampledata.Title
import mx.equipo6.proyectoapp.viewmodel.CalenVM
import java.util.*
/**
* @author: Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* Funcion padre de la vista del calendario
 */
@Composable
fun CalenView(viewModel: CalenVM) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }  // Estado para el diálogo de fechas guardadas
    val showAdditionalFeaturesDialog = remember { mutableStateOf(false) } // Estado para el nuevo pop-up

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            item { HeaderSection(showDialog) }

            item { CalendarSection(viewModel) }

            item { ActionButtons(viewModel, context, showDialog) }

            item { SelectedDateCard(viewModel) }

            item { NextCycleCard(viewModel) }

            if (showDialog.value) {
                item {
                    SavedDatesDialog(
                        context = context,
                        viewModel = viewModel,
                        onDismiss = { showDialog.value = false }
                    )
                }
            }

            item { AdditionalFeaturesCard(showAdditionalFeaturesDialog) }

            if (showAdditionalFeaturesDialog.value) {
                item {
                    AdditionalFeaturesDialogWithTabs(viewModel,
                        onDismiss = { showAdditionalFeaturesDialog.value = false }
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
/**
* @author Sebastian Espinoza | A01750311
* @param showDialog: MutableState<Boolean> este estado es para mostrar el pop-up
* Funcion para mostrar el header del calendario, contiene el titulo y el boton
* de ver las fechas de actividad sexual guardada
 */
@Composable
fun HeaderSection(showDialog: MutableState<Boolean>) {

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)

        ) {
            Title(
                "Calendario",
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()
        .padding(start = 10.dp)   ) {
        Button(
            onClick = { showDialog.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFb16ccd),
                contentColor = Color.White
            ),
            modifier = Modifier.height(40.dp)
                .shadow(2.dp, shape = MaterialTheme.shapes.medium),
            contentPadding = PaddingValues(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Ver lista de fechas guardadas:")
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* Funcion para mostrar el calendario en la parte media de la
* pantalla
 */
@Composable
fun CalendarSection(viewModel: CalenVM) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFdea8cb)) //Color del borde de calendario
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            EmbeddedCalendarView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onDateSelected = { dateInMillis ->
                    viewModel.updateSelectedDate(dateInMillis)
                }
            )
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* @param context: Context contexto de la aplicacion
* @param showDialog: MutableState<Boolean> este estado es para mostrar el pop-up
* Funcion para mostrar los botones de la parte inferior de la pantalla
 */
@Composable
fun ActionButtons(viewModel: CalenVM, context: Context, showDialog: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { viewModel.calculateNextCycle() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFdea8cb),
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(4.dp)
                .weight(1f)
                .height(40.dp)
                .shadow(2.dp, shape = MaterialTheme.shapes.medium),
            elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
            contentPadding = PaddingValues(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Siguiente ciclo",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                viewModel.saveDate(context)
                showDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFdea8cb),
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(4.dp)
                .weight(1f)
                .height(40.dp)
                .shadow(2.dp, shape = MaterialTheme.shapes.medium),
            elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
            contentPadding = PaddingValues(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Actividad sexual",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* Funcion para mostrar la fecha seleccionada debajo del calendario
 */
@Composable
fun SelectedDateCard(viewModel: CalenVM) {
    val selectedDate by viewModel.selectedDate

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFc596b4)),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "📅 Fecha seleccionada:",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = selectedDate,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* Funcion para mostrar la fecha estimada del siguiente ciclo menstrual
* despúes de calcularla
 */
@Composable
fun NextCycleCard(viewModel: CalenVM) {
    val calculatedDate by viewModel.calculatedDate
    val showAnimation by viewModel.showAnimation

    AnimatedVisibility(
        visible = showAnimation,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFc596b4)),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha estimada del siguiente ciclo:",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = calculatedDate,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param context: Context contexto de la aplicacion
* @param viewModel: CalenVM view model para la vista
* @param onDismiss: () -> Unit funcion para cerrar el pop-up
* Funcion para mostrar el pop-up de las fechas guardadas
 */
@Composable
fun SavedDatesDialog(
    context: Context,
    viewModel: CalenVM,
    onDismiss: () -> Unit
) {
    val savedDates by viewModel.savedDatesWithKeys

    LaunchedEffect(Unit) {
        viewModel.loadSavedDatesWithKeys(context)  // Cargar las fechas guardadas cuando se abre el diálogo
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFFF607A)
                )
            ) {
                Text("Cerrar", color = MaterialTheme.colorScheme.onSecondary)
            }
        },
        title = {
            Text(
                text = "Fechas guardadas con actividad sexual",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                items(savedDates) { (dateInMillis, dateText) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = dateText,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        // Botón para eliminar la fecha
                        IconButton(
                            onClick = {
                                viewModel.deleteDate(context, dateInMillis)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar fecha",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    )
}

/**
* @author Sebastian Espinoza | A01750311
* @param modifier: Modifier modificador para el widget
* @param onDateSelected: (Long) -> Unit funcion para seleccionar la fecha
* Funcion para crear el widget de calendario
 */
@Composable
fun EmbeddedCalendarView(modifier: Modifier = Modifier, onDateSelected: (Long) -> Unit) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            CalendarView(context).apply {
                setBackgroundColor(android.graphics.Color.parseColor("#d9bfd0"))
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

/**
* @author Sebastian Espinoza | A01750311
* @param showDialog: MutableState<Boolean> este estado es para mostrar el pop-up
* Carta para mostrar el pop-up de las funciones adicionales (Calculadora de días fértiles,
* consumo sano de pastilla de emergencia, fechas de uso de pastilla de emergencia)
 */
@Composable
fun AdditionalFeaturesCard(showDialog: MutableState<Boolean>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { showDialog.value = true },  // Mostrar el pop-up al hacer clic
        colors = CardDefaults.cardColors(containerColor = Color(0xFFb16ccd)),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Calculadoras adicionales",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* @param onDismiss: () -> Unit funcion para cerrar el pop-up
* Funcion para mostrar el pop-up de las funciones adicionales
 */
@Composable
fun AdditionalFeaturesDialogWithTabs(viewModel: CalenVM, onDismiss: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("Fechas Fértiles", "Pastilla de emergencia", "Fechas de uso  de pastilla")

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.width(600.dp),
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFC7A8BC)
                )
            ) {
                Text("Cerrar", color = MaterialTheme.colorScheme.onSecondary)
            }
        },
        title = {
            Text(
                text = "Calculadoras Adicionales",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp

            )
        },
        text = {
            Column(modifier = Modifier.width(600.dp))
                    {

                // TabRow para mostrar las pestañas
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.fillMaxWidth(),
                    contentColor = Color.Black
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTab == index) Color.Black else Color.Gray,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 15.sp
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contenido de las pestañas
                when (selectedTab) {
                    0 -> FertileWindowCalculator(viewModel)  // Calculadora de fechas fértiles
                    1 -> EmergencyPillCalculator(viewModel)  // Calculadora de pastilla de emergencia
                    2 -> EmergencyPillDates(viewModel)  // Fechas de consumo de pastilla de emergencia
                }
            }
        }
    )
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* Funcion para mostrar la calculadora de días fértiles
 */
@Composable
fun FertileWindowCalculator(viewModel: CalenVM) {
    var startDate by remember { mutableStateOf("") }
    val fertileWindow by viewModel.fertileWindow.collectAsState()

    Column {
        Text("Introduce el primer día de tu ciclo (inicio del sangrado):")
        Spacer(modifier = Modifier.height(8.dp))

        // Input para la fecha
        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("DD/MM/YYYY") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.calculateFertileWindow(startDate) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF607A),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular días fértiles")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el resultado de los días fértiles inmediatamente
        fertileWindow?.let { (startFertile, endFertile) ->
            Text("Tus días fértiles serán aproximadamente del $startFertile al $endFertile.")
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM
* Funcion para mostrar la calculadora de pastilla de emergencia
 */
@Composable
fun EmergencyPillCalculator(viewModel: CalenVM) {
    var lastPillDate by remember { mutableStateOf("") }
    var nextPillDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column {
        Text("Introduce la fecha de tu última pastilla de emergencia:")
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastPillDate,
            onValueChange = { lastPillDate = it },
            label = { Text("DD/MM/YYYY") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                nextPillDate = viewModel.calculateNextEmergencyPillDate(lastPillDate)

                // Guardar la fecha ingresada por el usuario como fecha de consumo de la pastilla de emergencia
                val dateParts = lastPillDate.split("/")
                if (dateParts.size == 3) {
                    val day = dateParts[0].toIntOrNull()
                    val month = dateParts[1].toIntOrNull()
                    val year = dateParts[2].toIntOrNull()

                    if (day != null && month != null && year != null) {
                        val calendar = Calendar.getInstance().apply {
                            set(Calendar.DAY_OF_MONTH, day)
                            set(Calendar.MONTH, month - 1)  // Los meses en Calendar son base 0
                            set(Calendar.YEAR, year)
                        }

                        // Guardar la fecha en milisegundos
                        viewModel.saveEmergencyPillDate(context, calendar.timeInMillis)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF607A),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular próxima pastilla")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (nextPillDate.isNotEmpty()) {
            Text("La próxima fecha segura de consumo es el $nextPillDate, " +
                    "debes esperar al menos hasta el siguiente ciclo menstrual.")
        }

        // Carta para mostrar la importancia de la pastilla de emergencia
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)), // Light red background to highlight warning
            elevation = CardDefaults.cardElevation(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "⚠️ Importante: Uso de la pastilla de emergencia",
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "La pastilla anticonceptiva de emergencia es una opción segura para prevenir el embarazo después " +
                            "de una relación sexual sin protección o en casos de falla de un método anticonceptivo. Sin embargo, " +
                            "no debe utilizarse como un método anticonceptivo regular. El uso frecuente de la pastilla puede reducir " +
                            "su efectividad y alterar tu ciclo menstrual, lo que puede llevar a irregularidades o efectos secundarios no deseados.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

/**
* @author Sebastian Espinoza | A01750311
* @param viewModel: CalenVM view model para la vista
* Funcion para mostrar la lista de fechas de consumo de la pastilla de emergencia
 */
@Composable
fun EmergencyPillDates(viewModel: CalenVM) {
    val context = LocalContext.current
    val savedPillDates by viewModel.savedEmergencyPillDates

    // Cargar las fechas al abrir la pestaña
    LaunchedEffect(Unit) {
        viewModel.loadSavedEmergencyPillDates(context)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(savedPillDates) { (dateInMillis, dateText) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = dateText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Botón para eliminar la fecha
                IconButton(
                    onClick = { viewModel.deleteEmergencyPillDate(context, dateInMillis) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar fecha",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
