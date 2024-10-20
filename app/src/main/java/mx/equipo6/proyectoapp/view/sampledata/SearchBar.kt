package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Composable que muestra una barra de búsqueda con un ícono y un campo de texto.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param modifier Modificador opcional para la barra de búsqueda.
 * @param height Altura de la barra de búsqueda.
 * @param cornerRadius Radio de las esquinas de la barra de búsqueda.
 * @param backgroundColor Color de fondo de la barra de búsqueda.
 * @param icon ImageVector del ícono a mostrar en la barra de búsqueda.
 * @param onValueChange Función lambda que se ejecuta cuando el valor del campo de texto cambia.
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    height: Dp = 54.dp,
    cornerRadius: Dp = 32.dp,
    backgroundColor: Color = Color.White,
    icon: ImageVector = Icons.Default.Search,
    onValueChange: (TextFieldValue) -> Unit,
    onClearClick: () -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(textState) {
        delay(1000)
        focusManager.clearFocus() // Clear focus after delay
    }

    Box(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 6.dp)
            .shadow(6.dp, shape = MaterialTheme.shapes.small.copy(all = CornerSize(cornerRadius)), clip = false)
            .clickable { focusManager.clearFocus() } // Clear focus when clicking outside
    ) {
        Surface(
            modifier = Modifier
                .height(height)
                .clip(shape = MaterialTheme.shapes.small.copy(all = CornerSize(cornerRadius)))
                .background(backgroundColor),
            color = MaterialTheme.colorScheme.surface
        ) {
            SearchBarContent(icon, textState, onValueChange = {
                textState = it
                onValueChange(it)
                focusRequester.requestFocus()
            }, onClearClick = {
                textState = TextFieldValue("")
                onClearClick()
            }, focusRequester)
        }
    }
}

/**
 * Composable que muestra el contenido de la barra de búsqueda.
 * @author Ulises Jaramillo Portilla | A01798380.
 * @param icon ImageVector del ícono a mostrar en la barra de búsqueda.
 * @param textState Estado del campo de texto.
 * @param onValueChange Función lambda que se ejecuta cuando el valor del campo de texto cambia.
 * @param focusRequester Objeto FocusRequester para gestionar el enfoque del campo de texto.
 */
@Composable
fun SearchBarContent(
    icon: ImageVector,
    textState: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClearClick: () -> Unit,
    focusRequester: FocusRequester
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = rememberVectorPainter(image = icon),
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier
                .size(40.dp)
                .padding(start = 3.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = textState,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        )
        if (textState.text.isNotEmpty()) {
            IconButton(onClick = onClearClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Search")
            }
        }
    }
}
