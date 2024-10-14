package mx.equipo6.proyectoapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginView(onLogin: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                .size(150.dp) // Tamaño de imagen más pequeño y consistente
        )

        Spacer(modifier = Modifier.height(16.dp)) // Standardized space

        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (email.isEmpty()) {
                    Text(
                        text = "Email",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = password,
            onValueChange = { password = it },
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
                    if (email.isNotBlank() && password.isNotBlank()) {
                        onLogin(email, password)
                    } else {
                        errorMessage = "Please fill in both fields"
                    }
                }
            ),
            decorationBox = { innerTextField ->
                if (password.isEmpty()) {
                    Text(
                        text = "Password",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    onLogin(email, password)
                } else {
                    errorMessage = "Please fill in both fields"
                }
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
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView { _, _ -> }
}