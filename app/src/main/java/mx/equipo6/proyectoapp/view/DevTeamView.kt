package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.equipo6.proyectoapp.R

/**
 * DevTeamView: Composable que muestra la información de los integrantes del equipo de desarrollo.
 * @param navController Controlador de navegación.
 * @author Ulises Jaramillo Portilla | A01798380.
 */
@Composable
fun DevTeamView(navController: NavHostController) {
    val students = listOf(
        Student("Ulises Jaramillo Portilla", "ITC", "A01798380"),
        Student("Julio Cesar Vivas Medina", "ITC", "A01749879"),
        Student("Sebastián Espinoza Farías", "ITC", "A01750311"),
        Student("Jesús Ángel Guzmán Ortega", "ITC", "A01799257"),
        Student("Manuel Olmos Antillón", "ITC", "A01750748")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Columna principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            // Fila para el botón de regresar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color(0x8DE7E1E1), shape = CircleShape)
                        .clip(CircleShape)
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Regresar",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 7.dp)
                )
            }

            // Divisor horizontal
            HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

            // Profile picture and team name
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.equipo6),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(64.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Equipo 6",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Student details in cards
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(students) { student ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4D0CB)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Nombre: ")
                                    }
                                    append(student.name)
                                }
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Carrera: ")
                                    }
                                    append(student.career)
                                }
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Matrícula: ")
                                    }
                                    append(student.matricula)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Student(
    val name: String,
    val career: String,
    val matricula: String
)