package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar {
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == NavigationRoutes.MapScreen.route } == true,
            onClick = { controller.navigate(NavigationRoutes.MapScreen.route) },
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Map",
                    tint =
                        if (navBackStackEntry?.destination?.hierarchy?.any {
                                it.route == "Map"
                            } == true
                        ) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                )
            },
        )
        Spacer(modifier = Modifier.width(16.dp))
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == NavigationRoutes.Home.route } == true,
            onClick = { controller.navigate(NavigationRoutes.Home.route) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "Home",
                    tint =
                        if (navBackStackEntry?.destination?.hierarchy?.any {
                                it.route == "home"
                            } == true
                        ) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                )
            },
        )
    }
}
