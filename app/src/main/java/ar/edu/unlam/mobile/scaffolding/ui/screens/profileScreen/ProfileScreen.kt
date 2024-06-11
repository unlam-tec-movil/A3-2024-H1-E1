package ar.edu.unlam.mobile.scaffolding.ui.screens.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationCellEdit
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
) {
    val userProfile by viewModel.currentUser
    val userPublications by viewModel.publications
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()

    LaunchedEffect(Unit) {
        userProfile?.userId?.let { userId ->
            viewModel.fetchPublications(userId)
            if (deleteSuccess) {
                //  Toast.makeText(context, "Publication deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        userProfile?.let { profile ->
            Column {
                Text(text = "Name: ${profile.displayName}")
                Text(text = "Email: ${profile.email}")
                AsyncImage(
                    model = profile.photoUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                )
            }
        } ?: Text("Profile is loading...")

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Publications")

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(userPublications) { publication ->
                PublicationCellEdit(
                    item = publication,
                    onClick = {},
                    onViewClick = {
                        controller.navigate(NavigationRoutes.PublicationScreen.route)
                    }, // lleva a PublicacionDetailScreen del currentUser

                    onEditClick = {
                        val publicationId = publication.id
                        controller.navigate(NavigationRoutes.PublicationScreen.withPublicationId(publicationId))
                    },
                    onDeleteClick = {
                        userProfile?.let { profile ->
                            viewModel.deletePublication(publication.id, profile.userId)
                        }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = { controller.popBackStack() }) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.viewModelScope.launch {
                        viewModel.signOut()
                    }
                    controller.navigate(NavigationRoutes.LoginScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            ) {
                Text("Cerrar sesión", color = Color.Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val controller = rememberNavController()
    ProfileScreen(controller = controller)
}
