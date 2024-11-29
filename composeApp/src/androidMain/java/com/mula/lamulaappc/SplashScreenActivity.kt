import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definición de colores
val primaryColor = Color(0, 0, 0) // Negro
val secondaryColor = Color(255, 255, 255) // Blanco

// Definición de fuentes
val primaryFont = FontFamily(Font(resId = R.font.avenir_roman)) // Asegúrate de tener la fuente en tu carpeta `res/font`

// Vista Principal
@Composable
fun LayerView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextView()
        }
    }
}

// Vista de Texto
@Composable
fun TextView() {
    Text(
        text = "Let’s get started",
        color = secondaryColor,
        fontSize = 20.sp,
        fontFamily = primaryFont,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .width(200.dp)
            .height(50.dp)
            .background(primaryColor, RoundedCornerShape(10.dp)) // Fondo con bordes redondeados
            .padding(10.dp) // Margen interno
    )
}

// Vista de Previsualización (Si usas Jetpack Compose Preview en Android Studio)
@Composable
fun PreviewLayerView() {
    LayerView()
}
