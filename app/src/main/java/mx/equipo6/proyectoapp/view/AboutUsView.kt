import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.viewmodel.AboutUsVM

val bellefair = FontFamily(Font(R.font.bellefair_regular))

@Preview(showBackground = true, widthDp = 400)
@Composable
fun AboutUsView(modifier: Modifier = Modifier, aboutUsVM: AboutUsVM = AboutUsVM()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        //color = Color(0xFFC7A8BC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp), // Eliminar padding superior
            verticalArrangement = Arrangement.Top, // Alinear la columna al tope de la pantalla
            horizontalAlignment = Alignment.CenterHorizontally // Centrar el contenido horizontalmente
        ) {
            // Título con estilo moderno
            Text(
                text = "¿Quiénes Somos?",
                fontFamily = bellefair,
                fontSize = 36.sp,
                fontWeight = FontWeight.Thin,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp) // Asegurar que no haya padding adicional
            )

            Spacer(modifier = Modifier.height(2.dp)) // Un espacio pequeño si es necesario

            // Primera tarjeta con la primera imagen
            Row {
                Card(
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF4D0CB),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(20.dp) // Espaciado vertical entre las cartas

                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally // Centrar la imagen horizontalmente
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.zazil),
                            contentDescription = "Zazil",
                            modifier = Modifier
                                .size(100.dp) // Tamaño de imagen más pequeño y consistente
                        )
                    }
                }

                // Segunda tarjeta con la segunda imagen
                Card(
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF4D0CB), // Diferente color para esta tarjeta
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(20.dp) // Espaciado vertical entre las cartas
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tb),
                            contentDescription = "TB",
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                }
            }
            Column{
                Text(
                    text = "Zazil es una marca comprometida con el bienestar de las mujeres y el cuidado del medio ambiente. Su misión es proporcionar soluciones innovadoras y sostenibles para el período menstrual. ¿Cómo lo hacen? A través de la creación de toallas femeninas reutilizables.",
                    fontFamily = bellefair,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Thin,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}