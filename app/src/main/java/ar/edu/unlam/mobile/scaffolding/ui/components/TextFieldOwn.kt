package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldOwn(
    tittle: String,
    placeholder: String,
    onResult: (String) -> Unit,
) {
    var value by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = tittle,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
        )
        TextField(
            value = value,
            onValueChange = { newText ->
                value = newText
                onResult(value)
            },
            modifier =
                Modifier
                    .fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 3.dp),
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldOwnPreview() {
    TextFieldOwn(tittle = "Email", placeholder = "abcd@gmail.com") {
    }
}
