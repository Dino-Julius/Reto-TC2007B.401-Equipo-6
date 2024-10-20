package mx.equipo6.proyectoapp.view

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.model.stripeAPI.PaymentActivity
import mx.equipo6.proyectoapp.model.validateCash
import mx.equipo6.proyectoapp.view.sampledata.OSMDroidMapView
import mx.equipo6.proyectoapp.viewmodel.AboutUsVM
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * AboutUsView: Muestra la información de la organización.
 * @author Jesús Guzmán | A01799257
 */

@Preview(showBackground = true, widthDp = 400)
@Composable
fun AboutUsView(modifier: Modifier = Modifier, aboutUsVM: AboutUsVM = AboutUsVM()) {
    val context = LocalContext.current // Obtener el contexto para abrir enlaces
    val bellefair = FontFamily(Font(R.font.bellefair_regular))
    var donationAmount by remember { mutableStateOf("") }
    var isValidDonation by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFFFFF)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                    .padding(top = 8.dp)
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
                        .padding(10.dp) // Espaciado vertical entre las cartas

                ) {
                    Column(
                        modifier = Modifier
                            .padding(13.dp),
                        horizontalAlignment = Alignment.CenterHorizontally // Centrar la imagen horizontalmente
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.zazil),
                            contentDescription = "Zazil",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(130.dp) // Tamaño de imagen más pequeño y consistente
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
                        .padding(10.dp) // Espaciado vertical entre las cartas
                ) {
                    Column(
                        modifier = Modifier
                            .padding(13.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tb),
                            contentDescription = "TB",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(130.dp)
                        )
                    }
                }
            }
            Column{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .shadow(6.dp, shape = RoundedCornerShape(8.dp), spotColor = Color(0xFFF4D0CB), ambientColor = Color(0xFFF4D0CB))
                        .border(1.dp, Color(0xFFF4D0CB), RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "Zazil es una organización sin ánimo de lucro comprometida con el bienestar de las mujeres y el cuidado del medio ambiente. Su misión es proporcionar soluciones innovadoras y sostenibles para el período menstrual. A través de la creación de toallas femeninas reutilizables.",
                        fontFamily = bellefair,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(12.dp)
                            .background(Color(0xFFF4D0CB), shape = RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    )
                }

                Text(
                    text = "Cambia el mundo con solo un gesto. Un peso, un apoyo.¡Ayúdanos a secar lágrimas, incluso con tu pequeña contribución!",
                    fontFamily = bellefair,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                )
                OutlinedTextField(
                    value = donationAmount,
                    onValueChange = {
                        donationAmount = it
                        isValidDonation = validateCash(it) // Use validateNumber function
                    },
                    label = { Text("Cantidad a donar") },
                    isError = !isValidDonation,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Number keyboard
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                if (!isValidDonation) {
                    Text(
                        text = "Ingrese una cantidad válida para donar",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Boton Donaciones
                Button(
                    onClick = {
                        val donationInCents = donationAmount.toDoubleOrNull()?.times(100)?.toInt()

                        if (donationInCents != null && donationInCents > 0) {
                            val intent = Intent(context, PaymentActivity::class.java)
                            intent.putExtra("totalPrice", donationInCents)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Ingrese una cantidad válida para donar", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFF4D0CB)),
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Donar",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(2.dp),
                        fontFamily = bellefair
                    )
                }

                Button(
                onClick = {
                    // Open PDF from raw resources
                    val pdfFile = File(context.cacheDir, "aviso.pdf")

                    // Copy PDF from raw resources to the temporary file
                    try {
                        // Get input stream for the raw resource
                        val inputStream: InputStream = context.resources.openRawResource(R.raw.aviso) // R.raw.aviso for the PDF in raw
                        val outputStream = FileOutputStream(pdfFile)

                        // Write the content of the PDF to the temporary file
                        inputStream.copyTo(outputStream)
                        inputStream.close()
                        outputStream.close()

                        // Open the PDF with an Intent
                        val pdfUri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", pdfFile)
                        val pdfIntent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(pdfUri, "application/pdf")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(pdfIntent)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Error opening PDF: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFF4D0CB)),
                modifier = Modifier
                    .padding(10.dp)
                    .clip(AbsoluteCutCornerShape(30.dp))
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Aviso de privacidad",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(2.dp),
                    fontFamily = bellefair
                )
            }
                // Redes Sociales Section
                Text(
                    text = "Visita nuestras redes sociales!",
                    fontFamily = bellefair,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                // Enlaces a redes sociales
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "Facebook",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/FundacionTodasBrillamos"))
                                context.startActivity(intent)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_tiktok),
                        contentDescription = "TikTok",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vm.tiktok.com/ZMjKEqyJH"))
                                context.startActivity(intent)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_youtube),
                        contentDescription = "YouTube",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@FundacionTodasBrillamos"))
                                context.startActivity(intent)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_instagram),
                        contentDescription = "Instagram",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/fundaciontodasbrillamos"))
                                context.startActivity(intent)
                            }
                    )
                }

                Spacer(modifier = Modifier.height(170.dp))

                // MAPS VIEW SECTION
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OSMDroidMapView()
                }
            }


                Spacer(modifier = Modifier.height(30.dp))
            // Informacion de contacto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .shadow(6.dp, shape = RoundedCornerShape(8.dp), spotColor = Color(0xFFF4D0CB), ambientColor = Color(0xFFF4D0CB))
                    .border(1.dp, Color(0xFFF4D0CB), RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "¡Ante cualquier duda o aclaración, llámanos y con gusto te atenderemos!\n" +
                            " +52 56 2808 3883\n" +
                            "\n" +
                            " Contacto@fundaciontodasbrillamos.org",
                    fontFamily = bellefair,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color(0xFFF4D0CB), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                )
            }
        }
    }
}
