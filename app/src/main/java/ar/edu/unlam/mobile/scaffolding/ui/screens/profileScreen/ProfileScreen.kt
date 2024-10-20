package ar.edu.unlam.mobile.scaffolding.ui.screens.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationCellEdit
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    publicationId: String,
) {
    val userProfile by viewModel.currentUser
    val userPublications by viewModel.publications
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()

    LaunchedEffect(Unit) {
        userProfile?.userId?.let { userId ->
            viewModel.fetchPublications(userId)
            if (deleteSuccess) {
                //    toast.show("Publication deleted")
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = rememberImagePainter(data = profile.photoUrl),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Text(text = "  ${profile.displayName}")
            }
        } ?: Text("El perfil esta cargando")

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Tus publicaciones",
            fontWeight = FontWeight.Bold,
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.publications.value) { publication ->
                PublicationCellEdit(
                    post = publication,
                    onClick = {
                        val publicationId = publication.id
                        controller.navigate(
                            NavigationRoutes.PublicationDetailsScreen.withPublicationId(
                                publicationId,
                            ),
                        )
                    },
                    onViewClick = {
                        val publicationId = publication.id
                        controller.navigate(
                            NavigationRoutes.PublicationDetailsScreen.withPublicationId(
                                publicationId,
                            ),
                        )
                    },
                    onEditClick = {
                        val publicationId = publication.id
                        controller.navigate(
                            NavigationRoutes.PublicationEditScreen.withPublicationId(
                                publicationId,
                            ),
                        )
                    },
                    onDeleteClick = {
                        userProfile?.let { profile ->
                            viewModel.deletePublication(publication.id, profile.userId)
                            viewModel.deletePublicationInAllPublications(publication.id)
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
