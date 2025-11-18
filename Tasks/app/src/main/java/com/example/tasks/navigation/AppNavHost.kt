package com.example.tasks.navigation

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
import com.example.tasks.MainViewModel
import com.example.tasks.models.BottomNavigationItem

@Composable
fun Navigation(
    viewModel: MainViewModel = viewModel(),
    navItems: List<BottomNavigationItem>
) {
    val selectedItemIndex by viewModel.selectedItemIndex.collectAsState()
    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    viewModel.selectedItem(index)
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
                            imageVector = if(index == selectedItemIndex ) {
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