@file:OptIn(InternalSerializationApi::class)

package ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import data.remote.model.CalificacionFinal
import data.remote.model.CalificacionUnidad
import data.remote.model.Cardex
import data.remote.model.CargaAcademica

import data.repository.SNRepository
import kotlinx.serialization.InternalSerializationApi
import ui.screens.MenuScreen
import ui.screens.ScreenCalificacionFinal
import ui.screens.ScreenCalificacionUnidad
import ui.screens.ScreenCardex
import ui.screens.ScreenCargaAcademica
import ui.screens.ScreenLogin
import ui.screens.ScreenProfile

//import ui.screens.*
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

    val scope = rememberCoroutineScope()

    // Ruta actual
    val backStack by navController
        .currentBackStackEntryAsState()

    val currentScreen = backStack
            ?.destination
            ?.route
            ?: SICEScreen.Login.name

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = SICEScreen.Login.name,
            modifier = Modifier.padding(padding)
        ) {

            //LOGIN
            composable(
                route = SICEScreen.Login.name
            ) {

                ScreenLogin(
                    viewModel = viewModel,
                    uiState = uiState,
                    onLoginSuccess = {
                        navController.navigate(SICEScreen.Menu.name) {
                            popUpTo(SICEScreen.Login.name) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            //MENÚ
            composable(
                route = SICEScreen.Menu.name
            ) {

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
                profile?.let {
                    ScreenProfile(it)
                }
            }

            //Carga academica
            composable(SICEScreen.Carga.name) {
                var cargas = remember { emptyList<CargaAcademica>() }
                LaunchedEffect(Unit) {
                    cargas = viewModel.cargaAcademica()
                }
                ScreenCargaAcademica(cargas)
            }

            //Cardex
            composable(SICEScreen.Cardex.name) {
                var cardex = remember { emptyList<Cardex>() }
                LaunchedEffect(Unit) {
                    cardex = viewModel.cardex(1)
                }
                ScreenCardex(cardex)
            }

            //Calificacion Unidad
            composable(SICEScreen.CalificacionUnidad.name) {
                var calificaciones = remember { emptyList<CalificacionUnidad>() }
                LaunchedEffect(Unit) {
                    calificaciones = viewModel.calificacionUnidad()
                }
                ScreenCalificacionUnidad(calificaciones)
            }

            //Calificacion Final
            composable(SICEScreen.CalificacionFinal.name) {
                var calificacionesFinal = remember { emptyList<CalificacionFinal>() }
                LaunchedEffect(Unit) {
                    calificacionesFinal = viewModel.calificacionFinal(1)
                }
                ScreenCalificacionFinal(calificacionesFinal)
            }
        }
    }
}