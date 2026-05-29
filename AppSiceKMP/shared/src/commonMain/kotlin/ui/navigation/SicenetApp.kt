@file:OptIn(InternalSerializationApi::class)

package ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import data.local.database.data.repository.SNRepository
import kotlinx.serialization.InternalSerializationApi
import org.jetbrains.compose.resources.stringResource
import ui.screens.*
import ui.viewmodel.*

@Composable
fun SicenetApp(
    repository: SNRepository
) {
    // Controlador de navegación
    val navController = rememberNavController()

    // Crear ViewModel
    val viewModel = remember { SNViewModel(repository) }

    // Estados
    val uiState by viewModel
        .uiState
        .collectAsState()

    val profile by viewModel
        .profile
        .collectAsState()

    val cargas by viewModel
        .cargaAcademica
        .collectAsState()

    val cardex by viewModel
        .cardex
        .collectAsState()

    val caliUnidad by viewModel
        .caliUnidad
        .collectAsState()

    val caliFinal by viewModel
        .caliFinal
        .collectAsState()

    val scope = rememberCoroutineScope()

    // Ruta actual
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SICEScreen.valueOf(
        backStackEntry?.destination?.route ?: SICEScreen.Profile.name
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar ={
            TopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = SICEScreen.LogIn.name,
            modifier = Modifier.padding(padding)
        ) {

            //LOGIN
            composable(route = SICEScreen.LogIn.name) {

                ScreenLogin(
                    viewModel = viewModel,
                    uiState = uiState,
                    onLoginSuccess = {
                        navController.navigate(SICEScreen.Menu.name) {
                            popUpTo(SICEScreen.LogIn.name) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            //MENÚ
            composable(route = SICEScreen.Menu.name) {
                MenuScreen(
                    onPerfilClick = {
                        viewModel.loadProfile()
                        navController.navigate(SICEScreen.Profile.name)
                    },

                    onCargaClick = {
                        navController.navigate(SICEScreen.Carga.name)
                    },

                    onCardexClick = {
                        navController.navigate(SICEScreen.Cardex.name)
                    },

                    onCaliUnidadClick = {
                        navController.navigate(SICEScreen.CalificacionUnidad.name)
                    },

                    onCaliFinalClick = {
                        navController.navigate(SICEScreen.CalificacionFinal.name)
                    }
                )
            }

            //PERFIL

            composable(route = SICEScreen.Profile.name) {
                if (profile == null) {
                    CircularProgressIndicator()
                } else {
                    ScreenProfile(profile!!)
                }
            }


            //Carga academica
            composable(SICEScreen.Carga.name) {
                LaunchedEffect(Unit) {
                    viewModel.loadCargaAcademica()
                }
                ScreenCargaAcademica(cargas)
            }
            //Cardex
            composable(SICEScreen.Cardex.name) {
                LaunchedEffect(Unit) {
                    viewModel.loadCardex()
                }
                ScreenCardex(cardex)
            }
            //Calificacion Unidad
            composable(SICEScreen.CalificacionUnidad.name) {
                LaunchedEffect(Unit) {
                    viewModel.loadCaliUnidad()
                }
                ScreenCalificacionUnidad(caliUnidad)
            }
            //Calificacion Final
            composable(SICEScreen.CalificacionFinal.name) {
                LaunchedEffect(Unit) {
                    viewModel.loadCaliFinal()
                }
                ScreenCalificacionFinal(caliFinal)
            }
        }
    }
}

@Composable
fun TopAppBar(
    currentScreen: SICEScreen,
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