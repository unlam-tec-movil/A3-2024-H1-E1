package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.example.compose.inverseOnSurfaceLight
import com.example.compose.onPrimaryDark
import com.example.compose.primaryDark

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar(
        containerColor = inverseOnSurfaceLight,
    ) {
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == NavigationRoutes.MapScreen.route } == true,
            onClick = { controller.navigate(NavigationRoutes.MapScreen.route) },
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = NavigationRoutes.MapScreen.route,
                    tint =
                        if (navBackStackEntry?.destination?.hierarchy?.any {
                                it.route == NavigationRoutes.MapScreen.route
                            } == true
                        ) {
                            primaryDark
                        } else {
                            onPrimaryDark
                        },
                )
            },
        )
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == NavigationRoutes.ListScreen.route } == true,
            onClick = { controller.navigate(NavigationRoutes.ListScreen.route) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = NavigationRoutes.ListScreen.route,
                    tint =
                        if (navBackStackEntry?.destination?.hierarchy?.any {
                                it.route == NavigationRoutes.ListScreen.route
                            } == true
                        ) {
                            primaryDark
                        } else {
                            onPrimaryDark
                        },
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar(controller = NavHostController(LocalContext.current))
}
