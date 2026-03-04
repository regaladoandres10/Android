/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)

package com.example.appsice.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.appsice.ui.screens.ScreenLogin
import com.example.appsice.viewmodel.SNViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.appsice.ui.navigation.SICEScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.work.WorkInfo
import com.example.appsice.ui.screens.MenuScreen
import com.example.appsice.ui.screens.ScreenCardex
import com.example.appsice.ui.screens.ScreenCargaAcademica
import com.example.appsice.ui.screens.ScreenProfile
import com.example.appsice.viewmodel.SNUiState
import kotlinx.serialization.InternalSerializationApi

@Composable
fun SicenetApp(
    navController: NavHostController = rememberNavController()
) {

    /*
    val marsViewModel: MarsViewModel =
        viewModel(factory = MarsViewModel.Factory)
*/
    val snViewModel: SNViewModel =
        viewModel(factory = SNViewModel.Factory)

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = SICEScreen.valueOf(
        backStackEntry?.destination?.route ?: SICEScreen.Profile.name
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
                startDestination = SICEScreen.LogIn.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ){
                composable(route = SICEScreen.LogIn.name) {
                    ScreenLogin(
                        snViewModel = snViewModel,
                        snUiState = snViewModel.snUiState,
                        //Navegar hacia la pantalla de profile
                        onLoginSuccesed = {
                            navController.navigate(SICEScreen.Menu.name) {
                                    popUpTo(SICEScreen.LogIn.name) { inclusive = true }
                            }
                        },
                    )
                }
                composable(route = SICEScreen.Menu.name) {
                    MenuScreen(
                        onPerfilClick = {
                            navController.navigate(SICEScreen.Profile.name)
                        },
                        onCargaClick = {
                            snViewModel.cargaAcademica()
                            navController.navigate(SICEScreen.Carga.name)
                        },
                        onCardexClick = {
                            snViewModel.cardex()
                            navController.navigate(SICEScreen.Cardex.name)
                        },
                        onCaliUnidadClick = { },
                        onCaliFinalClick = { }
                    )
                }
                composable(route = SICEScreen.Profile.name) {

                    val usuarios by snViewModel.usuarioFlow.collectAsState(initial = emptyList())
                    val workInfo by snViewModel.syncState.collectAsState(initial = null)

                    when {
                        workInfo?.state == WorkInfo.State.RUNNING -> {
                            CircularProgressIndicator()
                        }
                        workInfo?.state == WorkInfo.State.FAILED -> {
                            Text("Error al cargar el perfil")
                        }
                        usuarios.isNotEmpty() -> {
                            ScreenProfile(profile = usuarios.first())
                        }
                    }

                }
                composable(route = SICEScreen.Carga.name) {
                    val cargas by snViewModel.cargaFlow.collectAsState(initial = emptyList())
                    val cargaState by snViewModel.cargaState.collectAsState(initial = null)
                    when {
                        cargaState?.state == WorkInfo.State.RUNNING -> {
                            CircularProgressIndicator()
                        }

                        cargaState?.state == WorkInfo.State.FAILED -> {
                            Text("Error")
                        }

                        cargas.isNotEmpty() -> {
                            ScreenCargaAcademica(cargas)
                        }
                    }
                }
                composable(route = SICEScreen.Cardex.name) {
                    val cardexs by snViewModel.cardexFlow.collectAsState(initial = emptyList())
                    val cardexState by snViewModel.cardexState.collectAsState(initial = null)
                    when {
                        cardexState?.state == WorkInfo.State.RUNNING -> {
                            CircularProgressIndicator()
                        }

                        cardexState?.state == WorkInfo.State.FAILED -> {
                            Text("Error")
                        }

                        cardexs.isNotEmpty() -> {
                            ScreenCardex(cardexs)
                        }
                    }
                }
            }

            /*
            when(snViewModel.snUiState) {
                is SNUiState.Success -> {
                    Text("Inicio de sesión")
                }
                else -> {
                    ScreenLogin(
                        snViewModel = snViewModel,
                        snUiState = snViewModel.snUiState,
                        contentPadding = it
                    )
                }
            }

            HomeScreen(
                marsUiState = marsViewModel.marsUiState,
                retryAction = marsViewModel::getMarsPhotos ,
                contentPadding = it
            )*/
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
