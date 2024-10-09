// Update SearchBar.kt
package mx.equipo6.proyectoapp.view.sampledata

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    height: Dp = 54.dp,
    cornerRadius: Dp = 32.dp,
    backgroundColor: Color = Color.White,
    icon: ImageVector = Icons.Default.Search,
    onValueChange: (TextFieldValue) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 6.dp)
            .shadow(6.dp, shape = MaterialTheme.shapes.small.copy(all = CornerSize(cornerRadius)), clip = false) // Ensure shadow is not clipped
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
            })
        }
    }
}

@Composable
fun SearchBarContent(
    icon: ImageVector,
    textState: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}