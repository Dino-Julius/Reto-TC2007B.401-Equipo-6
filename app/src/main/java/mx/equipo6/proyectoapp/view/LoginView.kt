package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.equipo6.proyectoapp.viewmodel.LoginViewModel

/**
 * LoginView: Composable que define la vista de inicio de sesión.
 * @param onSignUp Función para navegar a SignUpView.
 * @param onForgotPassword Función para navegar a ForgotPasswordView.
 * @param viewModel ViewModel de la vista de inicio de sesión.
 * @author Manuel Olmos Antillón | A01750748
 */
@Composable
fun LoginView(
    onSignUp: () -> Unit,
    onForgotPassword: () -> Unit,  // Function to navigate to ForgotPasswordView
    viewModel: LoginViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = mx.equipo6.proyectoapp.R.drawable.zazil),
            contentDescription = "Zazil",
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.email.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (viewModel.email.value.isEmpty()) {
                        Text(
                            text = "Correo",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.passwordLogin()
                }
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (viewModel.password.value.isEmpty()) {
                        Text(
                            text = "Contraseña",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    innerTextField()
                }
            }
        )

        viewModel.errorMessage.value?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                viewModel.passwordLogin()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC7A8BC),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = Color.Blue,
            modifier = Modifier.clickable { onForgotPassword() }  // Navigates to ForgotPasswordView
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crear cuenta",
            color = Color.Blue,
            modifier = Modifier.clickable { onSignUp() }
        )
    }
}