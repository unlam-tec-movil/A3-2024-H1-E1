@file:OptIn(ExperimentalMaterial3Api::class)

package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.example.compose.inverseOnSurfaceLight
import com.example.compose.onPrimaryDark

@Composable
fun SearchBox(
    initialText: String = "",
    placeholderText: String = "Search",
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {},
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    listForSearch: State<List<PublicationCellModel>>? = null,
    filterList: (String) -> Unit = {},
    controller: NavHostController? = null,
) {
    var text by remember { mutableStateOf(initialText) }
    var active by remember { mutableStateOf(false) }

    val widthFraction = animateFloatAsState(if (active) 1f else 0.96f)

    SearchBar(
        modifier =
            Modifier
                .fillMaxWidth(widthFraction.value)
                .background(Color.Transparent),
        query = text,
        onQueryChange = { newText ->
            text = newText
            // filterList(newText)
            onQueryChange(newText)
        },
        onSearch = {
            active = false
            onSearch()
        },
        active = active,
        onActiveChange = { isActive ->
            active = isActive
            onActiveChange(isActive)
        },
        placeholder = {
            Text(placeholderText)
        },
        leadingIcon = {
            if (active) {
                Icon(
                    modifier =
                        Modifier.clickable {
                            active = false
                        },
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Icon",
                )
            } else {
                Icon(
                    modifier = Modifier.clickable(onClick = onLeadingIconClick),
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Filter Icon",
                    tint = onPrimaryDark,
                )
            }
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier =
                        Modifier
                            .clickable {
                                if (text.isNotEmpty()) {
                                    text = ""
                                } else {
                                    active = false
                                }
                            },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Filter Icon",
                )
            } else {
                Icon(
                    modifier = Modifier.clickable(onClick = onTrailingIconClick),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Icon",
                    tint = onPrimaryDark,
                )
            }
        },
        shadowElevation = 4.dp,
        colors =
            SearchBarDefaults.colors(
                containerColor = inverseOnSurfaceLight,
            ),
    ) {
        // /aca deberia poner el resultado de la busqueda
        LaunchedEffect(key1 = text) {
            filterList(text)
        }
        LazyColumn {
            items(listForSearch!!.value) { publication ->
                PublicationCell(
                    item = publication,
                    onClick = {
                        controller!!.navigate(
                            NavigationRoutes.PublicationScreen.withPublicationId(publication.id),
                        )
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    SearchBox()
}
