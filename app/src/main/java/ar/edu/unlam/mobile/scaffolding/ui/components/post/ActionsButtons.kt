package ar.edu.unlam.mobile.scaffolding.ui.components.post

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import com.example.compose.inverseOnSurfaceLight
import com.example.compose.onPrimaryDark

@Composable
fun ActionButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = onPrimaryDark,
            contentColor = inverseOnSurfaceLight,
        ),
    ) {
        Row {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    ActionButton(text = "cancelar", icon = painterResource(id = R.drawable.outline_flip_camera_ios_24)) {

    }
}
