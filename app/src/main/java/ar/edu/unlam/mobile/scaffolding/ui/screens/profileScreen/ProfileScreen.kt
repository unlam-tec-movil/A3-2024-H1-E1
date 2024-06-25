package ar.edu.unlam.mobile.scaffolding.ui.screens.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationCellEdit
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import coil.compose.rememberAsyncImagePainter
import com.example.compose.inverseOnSurfaceLight
import com.example.compose.onPrimaryDark
import com.example.compose.primaryDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(inverseOnSurfaceLight),
    ) {
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
                    // /aca debo poner un icono para ir atras
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Mi Perfil",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = onPrimaryDark,
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { controller.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = onPrimaryDark,
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                        },
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = inverseOnSurfaceLight,
                            ),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = profile.photoUrl),
                        contentDescription = "",
                        modifier =
                            Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = "Hola ${profile.displayName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = onPrimaryDark,
                    )
                }
            } ?: Text("Profile is loading...")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Mis publicaciones",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = onPrimaryDark,
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(userPublications) { publication ->
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
                                viewModel.deletePublication(publicationId, profile.userId)
                                viewModel.deletePublicationInAllPublications(publicationId)
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
                Button(
                    onClick = { controller.popBackStack() },
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = onPrimaryDark,
                        ),
                ) {
                    Text("Volver")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.signOut()
                        }
                        controller.navigate(NavigationRoutes.LoginScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryDark),
                ) {
                    Text("Cerrar sesión", color = onPrimaryDark)
                }
            }
        }
    }
}
