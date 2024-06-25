package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val navItemColors =
        NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.secondary,
            disabledIconColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = MaterialTheme.colorScheme.primary,
            selectedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            unselectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.primary,
        )
    NavigationBar {
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == NavigationRoutes.MapScreen.route } == true,
            onClick = { controller.navigate(NavigationRoutes.MapScreen.route) },
            colors = navItemColors,
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = NavigationRoutes.MapScreen.route,
                )
            },
        )
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == NavigationRoutes.ListScreen.route } == true,
            onClick = { controller.navigate(NavigationRoutes.ListScreen.route) },
            colors = navItemColors,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = NavigationRoutes.ListScreen.route,
                )
            },
        )
    }
}
