package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun QRFloatingButton(
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF795548),
    contentColor: Color = Color.White,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(contentColor = contentColor, containerColor = backgroundColor),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        contentPadding = PaddingValues(2.dp),
        modifier =
            Modifier.size(57.dp, 57.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.qr_code),
            contentDescription = "QR",
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .size(50.dp, 50.dp),
        )
    }
}
