package mx.equipo6.proyectoapp.view

import mx.equipo6.proyectoapp.viewmodel.SignUpViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

@Composable
fun ForgotPasswordView(navController: NavController, signUpViewModel: SignUpViewModel) {
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
                .size(130.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email input for password reset
        BasicTextField(
            value = signUpViewModel.email.value,
            onValueChange = { signUpViewModel.email.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    signUpViewModel.sendResetLink()
                }
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (signUpViewModel.email.value.isEmpty()) {
                        Text(
                            text = "Correo electrónico",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    innerTextField()
                }
            }
        )

        // Display error message if present
        signUpViewModel.errorMessage.value?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                signUpViewModel.sendResetLink()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC7A8BC),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(text = "Enviar enlace de restablecimiento")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate back to the login screen
        Text(
            text = "Volver a Login",
            color = Color.Blue,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
    }
}
