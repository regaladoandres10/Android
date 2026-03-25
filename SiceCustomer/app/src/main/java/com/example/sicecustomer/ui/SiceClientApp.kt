package com.example.sicecustomer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sicecustomer.ui.navigation.SiceClientScreen
import com.example.sicecustomer.ui.screens.CardexClientScreen
import com.example.sicecustomer.ui.screens.CargaAcademicaClientScreen
import com.example.sicecustomer.ui.screens.MenuSice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiceClient(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    //Dejar por defecto la pantalla de Menu
    val currentScreen = SiceClientScreen.valueOf(
        backStackEntry?.destination?.route ?: SiceClientScreen.Menu.name
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = SiceClientScreen.Menu.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = SiceClientScreen.Menu.name) {
                    MenuSice(
                        onCardexClick = {
                            navController.navigate(SiceClientScreen.Cardex.name)
                        },
                        onCargaClick = {
                            navController.navigate(SiceClientScreen.Carga.name)
                        }
                    )
                }
                composable(route = SiceClientScreen.Cardex.name) {
                    CardexClientScreen()
                }
                composable(route = SiceClientScreen.Carga.name) {
                    CargaAcademicaClientScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    currentScreen: SiceClientScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Screen"
                    )
                }
            }
        }
    )
}
