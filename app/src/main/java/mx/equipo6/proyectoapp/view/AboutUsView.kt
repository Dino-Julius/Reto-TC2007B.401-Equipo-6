import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
        color = Color(0xFFF4D0CB) // Color de fondo más moderno
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
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
                    .padding(vertical = 16.dp)
            )

            // Primera tarjeta con la primera imagen
            Row {
                Card(
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFD5507C),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(20.dp) // Espaciado vertical entre las cartas

                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally // Centra la imagen horizontalmente
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
                        containerColor = Color(0xFFD5507C), // Diferente color para esta tarjeta
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

            // Botón moderno
            Button(
                onClick = { /* Acción del botón */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Conócenos más", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}