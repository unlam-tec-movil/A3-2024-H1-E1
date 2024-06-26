package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun TextFieldPassword(
    tittle: String,
    placeholder: String,
    onResult: (String) -> Unit,
) {
    var visible by remember {
        mutableStateOf(false)
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = tittle,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
        )

        TextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
                onResult(password)
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
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
            trailingIcon = {
                val image =
                    if (visible) {
                        R.drawable.baseline_visibility_24
                    } else {
                        R.drawable.baseline_visibility_off_24
                    }
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFielPasswordPreview() {
    TextFieldPassword(tittle = "Password", placeholder = "password") {
    }
}
