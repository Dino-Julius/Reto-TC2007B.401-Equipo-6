package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable que define la vista de la página "Configuración" de la aplicación.
 * @param modifier Modificador de diseño.
 */
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.equipo6.proyectoapp.R
import mx.equipo6.proyectoapp.view.sampledata.bellefair
import mx.equipo6.proyectoapp.viewmodel.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigView(viewModel: SignUpViewModel = viewModel()) {
    val bellefair = FontFamily(Font(R.font.bellefair_regular))
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Actualización de perfil",
            fontFamily = bellefair,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            fontSize = 32.sp,
            color = Color(0xFF000000),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            @Composable
            fun styledTextField(value: String, label: String, onValueChange: (String) -> Unit) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = {
                        Text(
                            label,
                            fontSize = 18.sp // Increase the font size of the label
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0xFFF5F5F5),
                        focusedBorderColor = Color(0xFFC7A8BC),
                        unfocusedBorderColor = Color(0xFFD1D1D1)
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )
            }

            styledTextField(viewModel.firstName.value, "Nombre") { viewModel.firstName.value = it }
            styledTextField(viewModel.lastName.value, "Apellido") { viewModel.lastName.value = it }
            styledTextField(viewModel.email.value, "Correo electrónico") { viewModel.email.value = it }
            styledTextField(viewModel.phone.value, "Teléfono") { viewModel.phone.value = it }
            styledTextField(viewModel.address.value, "Dirección") { viewModel.address.value = it }
            styledTextField(viewModel.password.value, "Contraseña") { viewModel.password.value = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveUserData()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFb86ccc),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Guardar Cambios",
                fontSize = 18.sp
            )
        }
    }
}
