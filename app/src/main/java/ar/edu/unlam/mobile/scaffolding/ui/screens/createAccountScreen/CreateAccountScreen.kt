package ar.edu.unlam.mobile.scaffolding.ui.screens.createAccountScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.TextFieldOwn
import ar.edu.unlam.mobile.scaffolding.ui.components.TextFieldPassword
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import ar.edu.unlam.mobile.scaffolding.ui.theme.ArenaDark

@Composable
fun CreateAccountScreen(navHostController: NavHostController) {
    val createAccountVM: CreateAccountViewModel = hiltViewModel()
    val context = LocalContext.current
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp),
    ) {
        // /crear el text de crear cuenta
        IconButton(
            onClick = {
                navHostController.navigate(NavigationRoutes.LoginScreen.route)
            },
            modifier =
                Modifier
                    .align(Alignment.Start),
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
            )
        }

        Column(
            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
        ) {
            Text(
                text = "Crea una cuenta",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldOwn(
                tittle = "Usuario",
                placeholder = "usuario",
            ) { name ->
                createAccountVM.setName(name)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldOwn(
                tittle = "Email",
                placeholder = "abcd@gmail.com",
            ) { email ->
                // /manejamos el resultado del email, lo validamos
                createAccountVM.setEmail(email)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldPassword(
                tittle = "Contraseña",
                placeholder = "Pepito18",
            ) { password ->
                // manejamos el resultado de la contraseña aca
                createAccountVM.setPassword(password)
            }
            Text(
                text = "minimo 8 caracteres, una mayuscula y un numero",
                fontSize = 12.sp,
                color = Color.Red,
            )
        }

        ExtendedFloatingActionButton(
            onClick = {
                if (createAccountVM.validateEmail(createAccountVM.email.value) &&
                    createAccountVM.validatePassword(createAccountVM.email.value)
                ) {
                    createAccountVM.createNewAccount()
                    navHostController.navigate(NavigationRoutes.MapScreen.route)
                } else {
                    Toast.makeText(context, "email o contraseña invalida", Toast.LENGTH_LONG).show()
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp, bottom = 5.dp),
            containerColor = ArenaDark,
        ) {
            Text(
                text = "Crear cuenta",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White,
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "¿Ya tienes cuenta? ")
            TextButton(onClick = {
                navHostController.navigate(NavigationRoutes.LoginScreen.route)
            }) {
                Text(text = "Inicia sesion")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun createAccountPreview() {
    val navController = rememberNavController()
    CreateAccountScreen(navHostController = navController)
}
