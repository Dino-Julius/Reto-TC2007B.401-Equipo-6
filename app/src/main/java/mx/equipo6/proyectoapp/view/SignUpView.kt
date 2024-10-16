// SignUpView.kt
package mx.equipo6.proyectoapp.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import java.util.*

@Composable
fun SignUpView(onSignUp: (String, String, String, String, String, String, String, String) -> Unit, onLogin: () -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val genderOptions = listOf("hombre", "mujer", "otro")

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            birthDate = "$year-${month + 1}-$dayOfMonth"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

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

        BasicTextField(
            value = firstName,
            onValueChange = { firstName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (firstName.isEmpty()) {
                    Text(
                        text = "Nombre",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = lastName,
            onValueChange = { lastName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (lastName.isEmpty()) {
                    Text(
                        text = "Apellido",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (birthDate.isEmpty()) {
                    Text(
                        text = "Fecha de nacimiento (YYYY-MM-DD)",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        Button(
            onClick = { datePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC7A8BC),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(text = "Selecciona fecha de nacimiento")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(12.dp)
        ) {
            Text(
                text = if (gender.isEmpty()) "Género" else gender,
                color = if (gender.isEmpty()) Color.Gray else Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genderOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        gender = option
                        expanded = false
                    }, text = {Text(text = option)})
                }
            }
        }

        BasicTextField(
            value = phone,
            onValueChange = { phone = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (phone.isEmpty()) {
                    Text(
                        text = "Teléfono",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (email.isEmpty()) {
                    Text(
                        text = "Correo",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (address.isEmpty()) {
                    Text(
                        text = "Dirección",
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
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (password.isEmpty()) {
                    Text(
                        text = "Contraseña",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                if (confirmPassword.isEmpty()) {
                    Text(
                        text = "Confirma Contraseña",
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
                if (firstName.isNotBlank() && lastName.isNotBlank() && birthDate.isNotBlank() && gender.isNotBlank() && phone.isNotBlank() && email.isNotBlank() && address.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                    if (password == confirmPassword) {
                        onSignUp(firstName, lastName, birthDate, gender, phone, email, address, password)
                    } else {
                        errorMessage = "Passwords do not match"
                    }
                } else {
                    errorMessage = "Please fill in all fields"
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC7A8BC),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Volver a Login",
            color = Color.Blue,
            modifier = Modifier.clickable { onLogin() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SignUpView(onSignUp = { _, _, _, _, _, _, _, _ -> }, onLogin = {})
}