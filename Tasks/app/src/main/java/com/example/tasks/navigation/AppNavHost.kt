package com.example.tasks.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tasks.MainViewModel
import com.example.tasks.models.BottomNavigationItem

@Composable
fun Navigation(
    //viewModel: MainViewModel = viewModel(),
    navController: NavController,
    navItems: List<BottomNavigationItem>,
) {
    //val selectedItemIndex by viewModel.selectedItemIndex.collectAsState()
    //Observar la ruta actual automaticamente
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        navItems.forEach { item ->
            NavigationBarItem(
                //Comparamos la ruta del item con la ruta actual
                selected = currentRoute == item.route,
                onClick = {
                    //Logica de navegación
                    navController.navigate(item.route) {
                        //Limpiar la pila para no acomular pantallas
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        //Evita copias multiples de la misma pantalla si se pulsa mucho el botón
                        launchSingleTop = true
                        //Restaurar el estado al volver
                        restoreState = true
                    }
                },
                //Mostrar el nombre de la Screen
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                //Mostrar el numero de notificaciones
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if(currentRoute == item.route ) {
                                item.selectedIcon
                            } else item.unSelectedIcon,
                            contentDescription = item.title,
                        )
                    }
                }
            )
        }
    }
}
