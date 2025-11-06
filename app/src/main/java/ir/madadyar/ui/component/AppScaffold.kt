package ir.madadyar.ui.component

import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ir.madadyar.navigation.Screen
import ir.madadyar.ui.theme.*

data class BottomItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    onClickProfile: () -> Unit,
    onClickSearch: () -> Unit,
    content: @Composable (innerPadding: androidx.compose.foundation.layout.PaddingValues) -> Unit
) {
    val items = listOf(
        BottomItem(Screen.Home.route, "خانه", Icons.Filled.Home),
        BottomItem("books_list", "کتاب‌ها", Icons.Filled.Menu),
        BottomItem("videos_list", "ویدیوها", Icons.Filled.PlayArrow)
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "مددیار", fontFamily = iransansFontFamily, color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onClickSearch) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = White)
                    }
                    IconButton(onClick = onClickProfile) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
            )
        },
        bottomBar = {
            Surface(
                color = androidx.compose.ui.graphics.Color.Transparent,
                shadowElevation = 8.dp,
                modifier = androidx.compose.ui.Modifier.padding(top = 4.dp)
            ) {
            NavigationBar(
                containerColor = DarkSurface,
                modifier = androidx.compose.ui.Modifier.height(58.dp)
            ) {
                items.forEach { item ->
                    val selected = currentDestination.isRouteInHierarchy(item.route)
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(0) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        label = { Text(item.label, fontFamily = iransansFontFamily, fontSize = 9.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal, color = if (selected) Yellow else White.copy(alpha = 0.8f)) },
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                tint = if (selected) Yellow else White.copy(alpha = 0.9f),
                                modifier = androidx.compose.ui.Modifier.size(18.dp)
                            )
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Yellow,
                            unselectedIconColor = White.copy(alpha = 0.9f),
                            selectedTextColor = Yellow,
                            unselectedTextColor = White.copy(alpha = 0.8f),
                            indicatorColor = Yellow.copy(alpha = 0.15f)
                        )
                    )
                }
            }
            }
        },
        containerColor = DarkBackground
    ) { inner ->
        content(inner)
    }
}

private fun NavDestination?.isRouteInHierarchy(route: String): Boolean {
    return this?.hierarchy?.any { it.route?.startsWith(route) == true } == true
}


